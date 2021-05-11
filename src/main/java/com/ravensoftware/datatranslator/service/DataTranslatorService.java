package com.ravensoftware.datatranslator.service;

import com.ravensoftware.datatranslator.constants.MessageConstants;
import com.ravensoftware.datatranslator.entity.FileRequest;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;


/**
 * Created by bilga
 */
@Service
public class DataTranslatorService {

    public String readLine(FileRequest fileRequest) {

        String errorMessage = validateFileRequest(fileRequest);
        if (errorMessage != null && !errorMessage.isEmpty()){
            return errorMessage;
        }

        // final new table data
        StringBuffer inputBuffer = new StringBuffer();

        // Map<old_column_name, new_column_name>
        Map<String, String> newColumnName =  new HashMap<>();
        newColumnName = getMapFromFile(fileRequest.getColumnDefinitionFilePath(), newColumnName);
        // Map<old_id_value, new_id_value>
        Map<String, String> newRowValue = new HashMap<>();
        newRowValue = getMapFromFile(fileRequest.getRowDefinitionFilePath(), newRowValue);

        try {
            // to hold skip column indexes
            List<Integer> skipColumnIndex = new ArrayList<>();

            File f = new File(fileRequest.getTableFilePath());
            BufferedReader b = new BufferedReader(new FileReader(f));
            String line = "";
            boolean columnNameLine = true;

            // read file rows and prepare rows for new file
            while ((line = b.readLine()) != null) {
                String row[] = line.split("\\s+");

                if (columnNameLine){
                    for (int i = 0; i < row.length; i++) {
                        if (!newColumnName.containsKey(row[i])){
                            skipColumnIndex.add(i);
                        }
                    }
                }

                if (columnNameLine){
                    for (int i = 0; i < row.length; i++) {
                        if (!skipColumnIndex.contains(i)){
                            inputBuffer.append(newColumnName.get(row[i])+ "\t");
                        }
                    }
                    inputBuffer.append('\n');
                    columnNameLine = false;
                } else {
                    StringBuffer rowStr = new StringBuffer();
                    for (int j = 0; j < row.length; j++) {
                        if (!skipColumnIndex.contains(j)){
                            if (newRowValue.containsKey(row[0])){
                                if (j == 0){
                                    rowStr.append(newRowValue.get(row[j]) + "\t");
                                } else {
                                    rowStr.append(row[j] + "\t");
                                }
                            }
                        }
                    }
                    if (rowStr.length() > 0){
                        inputBuffer.append(rowStr);
                        inputBuffer.append('\n');
                    }
                }
            }

            b.close();

            // write the new string with the replaced line OVER the new file
            FileOutputStream fileOut = new FileOutputStream(fileRequest.getNewFilePath());
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();
        } catch (FileNotFoundException ex){
            // TODO : add Exception Handling
            ex.printStackTrace();
            return MessageConstants.FILE_NOT_FOUND;
        } catch (IOException e){
            // TODO : add Exception Handling
            e.printStackTrace();
            return MessageConstants.IO_EXCEPTION;
        }

        return MessageConstants.DONE;
    }

    private Map<String, String> getMapFromFile (String filePath, Map<String, String> map){
        File f = new File(filePath);
        try (BufferedReader b = new BufferedReader(new FileReader(f))) {
            String inLine = "";
            while ((inLine = b.readLine()) != null) {
                String[] s = inLine.trim().split("\\s+");
                map.put(s[0], s[1]);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return map;
    }

    private String validateFileRequest(FileRequest fileRequest){
        StringBuilder errorMessage = new StringBuilder();
        if (fileRequest == null){
            errorMessage.append(MessageConstants.NULL_REQUEST);
            errorMessage.append('\n');
        } else {
            if (fileRequest.getColumnDefinitionFilePath() == null || fileRequest.getColumnDefinitionFilePath().isEmpty()){
                errorMessage.append(MessageConstants.NULL_COLUMN_DEFINITION_FILE_PATH);
                errorMessage.append('\n');
            }

            if (fileRequest.getNewFilePath() == null || fileRequest.getNewFilePath().isEmpty()){
                errorMessage.append(MessageConstants.NULL_NEW_FILE_PATH);
                errorMessage.append('\n');
            }

            if (fileRequest.getRowDefinitionFilePath() == null || fileRequest.getRowDefinitionFilePath().isEmpty()){
                errorMessage.append(MessageConstants.NULL_ROW_DEFINITION_FILE_PATH);
                errorMessage.append('\n');
            }

            if (fileRequest.getTableFilePath() == null || fileRequest.getTableFilePath().isEmpty()){
                errorMessage.append(MessageConstants.NULL_TABLE_FILE_PATH);
                errorMessage.append('\n');
            }
        }
        return errorMessage.toString();
    }
}
