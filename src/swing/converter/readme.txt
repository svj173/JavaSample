Преобразовать данные в форматы PDF, DOC...

1) Muhimbi PDF converter
 - http://www.muhimbi.com/Products/PDF-Converter-Services/summary.aspx?gclid=CODIjePw1bUCFfB3cAodVjgAUA
 - 1500$ - но есть free download

2)  iText - version 5.0.x
 2.1) http://sourceforge.net/projects/itext/ -- PDF only
 Внимание, поддержки форматов, в версиях:
 - 2.0.6 - html, pdf, rtf, xml  - WEdit.
 - 2.1.5 - html, pdf, xml       - JavaSample/freechart
 - 5.4.0 - pdf, html, xml
    - c 5 версии у него поддерживается только PDF - RTF уже отсутствует.  RTF вынесен в отдельный проект

 2.1)  itextrtf.sourceforge.net
  - нет файлов для загрузки

3) jpedal  - платная

4) jasperReports
 - http://jasperforge.org/projects/jasperreports
 - Экспорт в различные форматы данных: PDF, HTML, XLS, RTF, ODT, CSV, XML, XHTML, DOCX, XLSX - на основе шаблона JRXML.
 - Links
    - http://ru.wikipedia.org/wiki/JasperReports
    - http://voituk.kiev.ua/intro-jasper-reports/


Пожелания
1) Автономно.
 Чтобы не надо было устанавливать на комп OpenOfice, MSOfice и т.д. -- т.е. прога могла работать как в Windows, так и в Linux.

Выводы
1) RTF необходим.
2) jasperReports  - трудно, так как шаблон выводимых данных у каждой книги - свой.
 Это и применение или нет заголовков, нумерации и т.д.
 Т.е. здесь придется сначала сгенерить шаблон вывода JRXML. А потом гнать полный текст как XML.
3) Использовать старый iText. Но как тогда с PDF? Параллельно с новым iText?
4) Пока сделать по старинке, а потом уже улучшать.
