package com.epam.automation.api.business.model.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnalyzeLaunch {
    private int id;
    private String analyzerTypeName;
    private String analyzerHistoryMode;
    private List<String> analyzeItemsMode;
}
