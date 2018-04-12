//Поиск в google Maps (для работы с буфером требуется xclip)
package main

import (
	"fmt"
	"os"

	"github.com/atotto/clipboard"
	"github.com/toqueteos/webbrowser"
)

const reference string = `
ИМЯ

mapIt - Поиск в google Maps (для работы с буфером требуется xclip)

СИНТАКСИС

mapIt АДРЕС (если вставлять к командную строку) || mapIt (вставка из буфера обмена)

АВТОР

Виктор Соловьев 

СООБЩЕНИЕ ОБ ОШИБКАХ

Об ошибках сообщайте по адресу <viktor.vladimirovich.solovev@gmail.com>.  

АВТОРСКИЕ ПРАВА

Copyright 2017 Viktor Solovev

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.`

func main() {
	if len(os.Args) == 2 {
		if os.Args[1] == "-h" || os.Args[1] == "--help" {
			// вывод справки
			fmt.Println(reference)
		}
	} else if len(os.Args) == 1 {
		addr, _ := clipboard.ReadAll()
		googleMaps(addr)
	} else {
		var addr string
		for i := 1; i < len(os.Args)-1; i++ {
			addr += os.Args[i] + "+"
		}
		googleMaps(addr)
	}
}

// функция поиска
func googleMaps(url string) {
	webbrowser.Open("https://www.google.com/maps/place/" + url)
}
