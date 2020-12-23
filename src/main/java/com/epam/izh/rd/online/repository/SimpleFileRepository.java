package com.epam.izh.rd.online.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class SimpleFileRepository implements FileRepository {

  /**
   * Метод рекурсивно подсчитывает количество файлов в директории
   *
   * @param path путь до директори
   * @return файлов, в том числе скрытых
   */
  @Override
  public long countFilesInDirectory(String path) {
    File dir = new File("src/main/resources/" + path);
    long count = 0;
    if (dir.isDirectory()) {
      for (File file : dir.listFiles()) {
        count += countFilesInDirectory(path + "/" + file.getName());
      }

    } else {
      count++;
    }

    return count;
  }

  /**
   * Метод рекурсивно подсчитывает количество папок в директории, считая корень
   *
   * @param path путь до директории
   * @return число папок
   */
  @Override
  public long countDirsInDirectory(String path) {
    File dir = new File("src/main/resources/" + path);
    long count = 0;

    if (dir.isDirectory()) {
      for (File file : dir.listFiles()) {
        count += countDirsInDirectory(path + "/" + file.getName());
      }
      count++;
    }

    return count;
  }

  /**
   * Метод копирует все файлы с расширением .txt
   *
   * @param from путь откуда
   * @param to путь куда
   */
  @Override
  public void copyTXTFiles(String from, String to) {
    File fileFrom = new File(from);
    File fileTo = new File(to);
    try {
      Files.copy(fileFrom.toPath(), fileTo.toPath());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Метод создает файл на диске с расширением txt
   *
   * @param path путь до нового файла
   * @param name имя файла
   * @return был ли создан файл
   */
  @Override
  public boolean createFile(String path, String name) {
    File dir = new File(getClass().getResource("/").getPath() + "/" + path);

    if (!dir.exists()) dir.mkdir();

    File file = new File(dir + "/" + name);
    try {
      return file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Метод считывает тело файла .txt из папки src/main/resources
   *
   * @param fileName имя файла
   * @return контент
   */
  @Override
  public String readFileFromResources(String fileName) {
    StringBuilder filecontent;

    try (BufferedReader reader =
        new BufferedReader(new FileReader("src\\main\\resources\\" + fileName))) {
      filecontent = new StringBuilder(reader.readLine());
    } catch (IOException e) {
      return e.getMessage();
    }

    return filecontent.toString();
  }
}
