package main

import (
	"io"
	"log"
	"net"
	"os"
)

func main() {
	listen, err := net.Dial("tcp", "127.0.0.1:8888")
	if err != nil {
		log.Fatal(err)
	}
	defer listen.Close()
	mustCopy(os.Stdout, listen)
}

func mustCopy(dst io.Writer, src io.Reader) {
	if _, err := io.Copy(dst, src); err != nil {
		log.Fatal(err)
	}
}
