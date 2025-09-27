package main

import (
	"embed"
	"fmt"
	"io"
	"io/fs"
	"os"
	"path/filepath"
)

//go:embed config.yml
var resource embed.FS

var Embed = new(_embed)

type _embed struct{}

func (e *_embed) RestoreFolder(dir string) {
	entries, err := resource.ReadDir(dir)
	if err != nil {
		fmt.Println("[embed restore resource file]: err:", err)
		return
	}
	for i := 0; i < len(entries); i++ {
		if entries[i].IsDir() {
			e.RestoreFile(filepath.Join(dir, entries[i].Name()), entries[i])
			continue
		}
		e.RestoreFile(entries[i].Name(), entries[i])
	}
}

func (e *_embed) RestoreFile(path string, entry fs.DirEntry) {
	_, err := os.Stat(path)
	if entry.IsDir() {
		if os.IsNotExist(err) {
			fmt.Printf("[embed restore mkdir] dir:%s\n", path)
			err = os.Mkdir(path, os.ModePerm)
			if err != nil {
				fmt.Printf("[embed restore mkdir] err:%v\n", err)
				return
			}
		}
		var entries []fs.DirEntry
		entries, err = resource.ReadDir(path)
		if err != nil {
			return
		}
		for i := 0; i < len(entries); i++ {
			_, err = os.Stat(entries[i].Name())
			dirPath := filepath.Join(path, entries[i].Name())
			if os.IsNotExist(err) && entries[i].IsDir() {
				fmt.Println("[embed restore mkdir] dir:", dirPath)
				err = os.Mkdir(dirPath, os.ModePerm)
				if err != nil {
					fmt.Println("[embed restore mkdir] err:", err)
					return
				}
			}
			e.RestoreFile(dirPath, entries[i])
		}
	}

	if os.IsNotExist(err) && !entry.IsDir() {
		var src fs.File
		src, err = resource.Open(path)
		if err != nil {
			fmt.Println("[embed restore resource open file] open embed file failed, err:", err)
			return
		}
		var dst *os.File
		dst, err = os.Create(path)
		if err != nil {
			fmt.Println("[embed restore os create file] write err:", err)
			return
		}
		_, err = io.Copy(dst, src)
		if err != nil {
			fmt.Println("[embed restore io copy file] writer file failed, err:", err)
			return
		}
		defer func() {
			_ = src.Close()
			_ = dst.Close()
		}()
		return
	}
	fmt.Println("[embed restore resource file] file exist, path:", path)
}
