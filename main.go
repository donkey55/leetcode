package main

import (
	"sync"
)

func main() {
	var rw sync.RWMutex
	rw.Lock()
	//ctx, cancelFunc := context.WithCancel(context.TODO())
	//cancelFunc()

}
