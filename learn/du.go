package main

import (
	"flag"
	"fmt"
	"log"
	"os"
	"path/filepath"
	"sync"
	"time"
)

func cancel() bool {
	select {
	case <-done:
		return true
	default:
		return false
	}
}

func main() {
	flag.Parse()
	roots := flag.Args()
	if len(roots) == 0 {
		roots = []string{"."}
	}
	ch1 := make(chan int64)
	var n sync.WaitGroup
	for _, root := range roots {
		n.Add(1)
		go walkDir(root, &n, ch1)
	}
	go func() {
		os.Stdin.Read(make([]byte, 1))
		close(done)
	}()
	go func() {
		n.Wait()
		close(ch1)
	}()
	var sum int64
	var count int64 = 0
	tick := time.Tick(500 * time.Millisecond)
loop:
	for {
		select {
		case <-done:
			for range ch1 {
				// Do nothing.
			}
			return
		case x, ok := <-ch1:
			if !ok {
				break loop
			}
			count++
			sum += x
		case <-tick:
			printDiskUsage(count, sum)
		}
	}
	printDiskUsage(count, sum)
}

func walkDir(dir string, n *sync.WaitGroup, fileSizes chan<- int64) {
	defer n.Done()
	if cancel() {
		return
	}
	for _, entry := range dirents(dir) {
		if entry.IsDir() {
			n.Add(1)
			join := filepath.Join(dir, entry.Name())
			go walkDir(join, n, fileSizes)
		} else {
			info, err := entry.Info()
			if err != nil {
				log.Fatal(err)
			}
			fileSizes <- info.Size()
		}
	}
}

var sema = make(chan struct{}, 20)
var done = make(chan struct{})

func dirents(dir string) []os.DirEntry {
	select {
	case sema <- struct{}{}:
		fmt.Println("sema")
	case <-done:
		return nil
	}
	defer func() { <-sema }()
	readDir, err := os.ReadDir(dir)
	if err != nil {
		log.Fatal(err)
	}
	return readDir
}

func printDiskUsage(nfiles, nbytes int64) {
	fmt.Printf("%d files  %.1f GB\n", nfiles, float64(nbytes)/1e9)
}
