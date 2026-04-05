package com.epam.automation.core.data_reader;


import java.util.List;

public interface IDataReader<T> {
    List<T> readDataByKey(String filePath, String arrayKey);
}
