package downloader

import (
	"fmt"
	"io"
	"log"
	"net/http"
	"os"
	"path/filepath"
	"sync"
)

type Downloader struct {
	ThreadNum int
	file      *os.File
	mutex     sync.RWMutex
}

func New() *Downloader {
	return &Downloader{ThreadNum: 4}
}

func (d *Downloader) Download(url string, savePath string) error {
	if savePath == "" {
		savePath = filepath.Base(url)
	}
	// test url is right
	resp, err := http.Head(url)
	if err != nil {
		return err
	}
	defer d.file.Close()
	//fmt.Println(resp.Header.Get("Content-Disposition"))
	if resp.StatusCode == http.StatusOK && resp.Header.Get("Accept-Ranges") == "bytes" {
		return d.multiDownload(url, savePath, resp.ContentLength)
	}
	return d.singleDownload(url, savePath)
}

func (d *Downloader) multiDownload(url string, savePath string, fileSize int64) error {
	partSize := fileSize / int64(d.ThreadNum)
	var wg sync.WaitGroup
	wg.Add(d.ThreadNum)
	var start int64 = 0
	for i := 0; i < d.ThreadNum; i++ {
		go func(i int, start int64) {
			fmt.Println(start)
			defer wg.Done()
			var tempSize = partSize
			if i == d.ThreadNum-1 {
				tempSize = fileSize - start
			}
			d.downloadPart(url, savePath, start, tempSize)
		}(i, start)
		start += partSize
	}
	wg.Wait()
	return nil
}

func (d *Downloader) downloadPart(url, savePath string, byteStart, partSize int64) {
	//fmt.Println(byteStart)
	req, err := http.NewRequest("GET", url, nil)
	if err != nil {
		log.Fatal("create request error: " + err.Error())
	}
	if partSize > 0 {
		req.Header.Set("Range", fmt.Sprintf("bytes=%d-%d", byteStart, byteStart+partSize-1))
	} else {
		req.Header.Set("Range", fmt.Sprintf("bytes=%d-", byteStart))
	}
	do, err := http.DefaultClient.Do(req)
	if err != nil {
		log.Fatal("do request error: " + err.Error())
	}
	defer func(Body io.ReadCloser) {
		err := Body.Close()
		if err != nil {
			log.Fatal("close body error")
		}
	}(do.Body)
	d.mutex.RLock()
	if d.file == nil {
		d.mutex.RUnlock()
		d.mutex.Lock()
		d.file, err = os.OpenFile(savePath, os.O_RDWR|os.O_CREATE, 0666)
		if err != nil {
			d.mutex.Unlock()
			log.Fatal(err)
		}
		d.mutex.Unlock()
	} else {
		d.mutex.RUnlock()
	}
	d.mutex.Lock()
	_, err = d.file.Seek(byteStart, 0)
	if err != nil {
		log.Fatal(err)
	}
	defer d.mutex.Unlock()
	_, err = io.Copy(d.file, do.Body)
	if err != nil {
		fmt.Println("copy error")
		log.Fatal(err)
	}
	fmt.Println("finish")
}

func (d *Downloader) singleDownload(url string, savePath string) error {
	d.downloadPart(url, savePath, 0, 0)
	return nil
}
