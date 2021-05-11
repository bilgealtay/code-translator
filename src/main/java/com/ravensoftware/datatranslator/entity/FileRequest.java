package com.ravensoftware.datatranslator.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by bilga
 */
public class FileRequest implements Serializable {

    @NotNull
    private String tableFilePath;
    @NotNull
    private String columnDefinitionFilePath;
    @NotNull
    private String rowDefinitionFilePath;
    @NotNull
    private String newFilePath;

    public FileRequest() {
    }

    public FileRequest(@NotNull String tableFilePath, @NotNull String columnDefinitionFilePath, @NotNull String rowDefinitionFilePath, @NotNull String newFilePath) {
        this.tableFilePath = tableFilePath;
        this.columnDefinitionFilePath = columnDefinitionFilePath;
        this.rowDefinitionFilePath = rowDefinitionFilePath;
        this.newFilePath = newFilePath;
    }

    public String getTableFilePath() {
        return tableFilePath;
    }

    public void setTableFilePath(String tableFilePath) {
        this.tableFilePath = tableFilePath;
    }

    public String getColumnDefinitionFilePath() {
        return columnDefinitionFilePath;
    }

    public void setColumnDefinitionFilePath(String columnDefinitionFilePath) {
        this.columnDefinitionFilePath = columnDefinitionFilePath;
    }

    public String getRowDefinitionFilePath() {
        return rowDefinitionFilePath;
    }

    public void setRowDefinitionFilePath(String rowDefinitionFilePath) {
        this.rowDefinitionFilePath = rowDefinitionFilePath;
    }

    public String getNewFilePath() {
        return newFilePath;
    }

    public void setNewFilePath(String newFilePath) {
        this.newFilePath = newFilePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileRequest that = (FileRequest) o;
        return Objects.equals(tableFilePath, that.tableFilePath) &&
                Objects.equals(columnDefinitionFilePath, that.columnDefinitionFilePath) &&
                Objects.equals(rowDefinitionFilePath, that.rowDefinitionFilePath) &&
                Objects.equals(newFilePath, that.newFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableFilePath, columnDefinitionFilePath, rowDefinitionFilePath, newFilePath);
    }
}
