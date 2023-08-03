package svj.file.irdb;

import java.util.*;

/**
 * Статистика
 * 1) Всего Производителей  - директории первого уровня
 * 2) Поддиректории - всего: типы - остальное
 * 3) Есть ли вложенные поддиректории
 * 4) Файлы: колво - в типах устройств - остальные
 * <BR/>
 */
public class StatisticObj {

    // Всего Производителей  - директории первого уровня
    private int totalBrand;

    // ???
    private int subdirTypes;
    private int subdirOthers;
    private int subdirTotal;

    // вложенные поддиректории
    private int subSubDir;
    private Collection<String> subSubDirPath = new ArrayList<>();
    private Collection<String> subSub3DirPath = new ArrayList<>();

    private int fileTypeSize;
    private int fileOtherSize;
    // Всего файлов CSV
    private int totalFiles;

    public StatisticObj() {
        totalBrand = 0;
        subdirTypes = 0;
        subdirOthers = 0;
        subSubDir = 0;
        fileTypeSize = 0;
        fileOtherSize = 0;
    }

    public int getTotalBrand() {
        return totalBrand;
    }

    public void addTotalBrand() {
        totalBrand++;
    }

    public int getSubdirTypes() {
        return subdirTypes;
    }

    public void addSubdirTypes() {
        subdirTypes++;
    }

    public int getSubdirOthers() {
        return subdirOthers;
    }

    public void addSubdirOthers() {
        subdirOthers++;
    }

    public int getSubSubDir() {
        return subSubDir;
    }

    public void addSubSubDir() {
        subSubDir++;
    }

    public Collection<String> getSubDirPath() {
        return subSubDirPath;
    }

    public void addSubDirPath(String path) {
        this.subSubDirPath.add(path);
    }

    public Collection<String> getSub3DirPath() {
        return subSub3DirPath;
    }

    public void addSub3DirPath(String path) {
        this.subSub3DirPath.add(path);
    }

    public int getFileTypeSize() {
        return fileTypeSize;
    }

    public void addFileTypeSize() {
        fileTypeSize++;
    }

    public int getFileOtherSize() {
        return fileOtherSize;
    }

    public void addFileOtherSize() {
        fileOtherSize++;
    }

    public int getSubdirTotal() {
        return subdirTotal;
    }

    public void addSubdirTotal() {
        subdirTotal++;
    }

    public int getTotalFiles() {
        return totalFiles;
    }

    public void addTotalFiles() {
        totalFiles++;
    }

    @Override
    public String toString() {
        return "StatisticObj:" +
                "\n - totalBrand=" + totalBrand +
                "\n - subdirTypes=" + subdirTypes +
                "\n - subdirOthers=" + subdirOthers +
                "\n - subdirTotal=" + subdirTotal +
                "\n - subSubDir=" + subSubDir +
                "\n - subSubDirPath=" + subSubDirPath +
                "\n - subSub3DirPath=" + subSub3DirPath +
                "\n - fileTypeSize=" + fileTypeSize +
                "\n - fileOtherSize=" + fileOtherSize +
                "\n - totalFiles=" + totalFiles;
    }
}
