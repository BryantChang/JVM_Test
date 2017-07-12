package observator;

import java.io.*;

/**
 * Created by bryantchang on 2017/7/9.
 * 文件拷贝
 * 判读拷贝的源文件是否存在
 * 判断目标文件的父路径是否存在,不存在则创建
 * 进行文件拷贝操作
 */
class CopyUtil {
    private CopyUtil(){}  //构造方法私有化

    /**
     *
     * @param path
     * @return
     */
    public static boolean fileExists(String path) {
        return new File(path).exists();
    }

    public static void createParentDir(String path) {
        File file = new File(path);
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    public static boolean copy(String srcPath, String dstPath) {
        boolean flag = false;
        File inFile = new File(srcPath);
        File outFile = new File(dstPath);
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(inFile);
            output = new FileOutputStream(outFile);
            copyHandle(input, output);
            flag = true;
        }catch(Exception e) {
            flag = false;
        }finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }


    public static void copyHandle(InputStream input, OutputStream output) throws IOException {
        int tmp = 0;
        byte data[] = new byte[2048];
        while((tmp = input.read(data)) != -1) {
            output.write(data,0,tmp);
        }
//        do {
//            tmp = input.read();
//            output.write(tmp);
//        }while(tmp != -1);
    }
}
public class Copy {
    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("usage:");
            System.exit(1);
        }

        if(CopyUtil.fileExists(args[0])) {
            CopyUtil.createParentDir(args[1]);
            System.out.println(CopyUtil.copy(args[0], args[1]) ? "succ" : "fail");
        }else {
            System.out.println("source file not exists");
        }
    }
}
