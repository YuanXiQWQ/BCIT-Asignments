package ConsoleApp;
import java.io.*;

public class Activity {
   public static void main(String[] args) throws IOException {new Activity().onCreate();}
   public Activity() {}
    public void onCreate() {
       // 启动一个后台线程
       AsyncTask uploadAsyncTask = new UploadAsyncTask().execute();
       System.out.println("Waiting for Callback");
       try {
           BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
           br.readLine();
       } catch (Exception e) {/**/}
    }
}

class UploadAsyncTask extends AsyncTask {
    @Override
    protected void onPostExecute(String result) {
        // 完成后回调打印结果
        System.out.println(result);
    }

    @Override
    protected String doInBackground() {
        // 后台调用上传文件
        return new UploadClient().uploadFile();
    }
}
