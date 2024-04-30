package main

import (
	"fmt"
	"gopl.io/ch8/thumbnail"
	"log"
	"os"
	"sync"
	"time"
)

func main() {
	abort := make(chan struct{})

	go func() {
		os.Stdin.Read(make([]byte, 1))
		abort <- struct{}{}
	}()
	tick := time.Tick(1 * time.Second)
	for count := 10; count >= 0; count-- {
		fmt.Println(count)
		select {
		case <-tick:
			//
		case <-abort:
			fmt.Println("Aborting......")
			return
		}
	}
	launch()
}

func launch() {
	fmt.Println("Lift off!")
}

func makeThumbnail(filename <-chan string) int64 {
	sizes := make(chan int64)
	var wg sync.WaitGroup
	for f := range filename {
		wg.Add(1)
		go func(f string) {
			defer wg.Done()
			file, err := thumbnail.ImageFile(f)
			if err != nil {
				log.Println(err)
				return
			}
			stat, err := os.Stat(file)
			sizes <- stat.Size()
		}(f)
	}
	go func() {
		wg.Wait()
		close(sizes)
	}()
	var total int64
	for size := range sizes {
		total += size
	}
	return total
}
