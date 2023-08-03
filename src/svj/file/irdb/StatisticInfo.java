package svj.file.irdb;

import java.util.*;

/**
 * <BR/>
 */
public class StatisticInfo {

    private final String brandName;
    // Сколкьо Прооизхводителей использует этот тип устройства.
    private int count;
    // Пути файлов этих Производителей.
    private Collection<String> useFiles;
    // Сколкьо файлов-сутрйоств описано для данного типа
    private int fileCount;


    public StatisticInfo(String brandName) {
        this.brandName = brandName;
        count = 0;
        useFiles = new ArrayList<>();
    }

    public String getBrandName() {
        return brandName;
    }

    public int getCount() {
        return count;
    }

    public void addCount() {
        count++;
    }

    public Collection<String> getUseFiles() {
        return useFiles;
    }

    public void addUseFiles(String filePath) {
        this.useFiles.add(filePath);
    }

    public int getFileCount() {
        return fileCount;
    }

    public void addFileCount(int size) {
        fileCount = fileCount + size;
    }

    @Override
    public String toString() {
        return "" +
                //"\n   - brandName='" + brandName + '\'' +
                "\n   - count = " + count +
                "\n   - fileCount = " + fileCount +
                "\n   - providers = " + useFiles;
    }
}
