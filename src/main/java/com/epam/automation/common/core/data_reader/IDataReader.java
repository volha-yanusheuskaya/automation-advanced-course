package com.epam.automation.common.core.data_reader;


import java.util.List;

public interface IDataReader<T> {
    List<T> readDataByKey(String filePath, String arrayKey);
}
