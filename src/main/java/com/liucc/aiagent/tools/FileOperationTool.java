package com.liucc.aiagent.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import com.liucc.aiagent.constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * 文件操作工具类
 */
public class FileOperationTool {
    private final static String FILE_SAVE_DIR = FileConstant.FILE_SAVE_DIR + "/file";
    /**
     * 读取指定文件内容
     * @param fileName
     * @return
     */
    @Tool
    public String readFile(@ToolParam(description = "Name of the file to read") String fileName){
        String filePath = FILE_SAVE_DIR + "/" + fileName;
        try {
            return FileUtil.readUtf8String(filePath);
        } catch (IORuntimeException e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    /**
     * 写入内容到指定文件
     * @param fileName
     * @param content
     * @return
     */
    @Tool
    public String writeFile(@ToolParam(description = "Name of the file to read") String fileName,
                            @ToolParam(description = "Content to write to the file") String content) {
        String filePath = FILE_SAVE_DIR + "/" + fileName;
        try {
            FileUtil.mkdir(FILE_SAVE_DIR);
            FileUtil.writeUtf8String(content, filePath);
            return "File written successfully: " + filePath;
        } catch (IORuntimeException e) {
            return "Error writing file: " + e.getMessage();
        }
    }
}
