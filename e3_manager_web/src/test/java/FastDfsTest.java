import org.csource.fastdfs.*;
import org.junit.Test;
import yp.e3mall.common.utils.FastDFSClient;

/**
 * @author RickYinPeng
 * @ClassName FastDfsTest
 * @Description 测试FastDfs上传图片
 * @date 2018/10/5/13:37
 */
public class FastDfsTest {

    @Test
    public void testUpload() throws Exception{
        //创建一个配置文件，文件名任意。内容就是tracker服务器的地址
        //使用全局对象加载配置文件
        ClientGlobal.init("E:\\IdeaWorpace\\e_Store\\e3_manager_web\\src\\main\\resources\\conf\\client.conf");

        //创建TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获得一个TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //创建一个StorageServer的引用，可以是null
        StorageServer storageServer = null;
        //创建一个StorageCilent，参数需要TrackServer和StorageServer
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        //使用StorageCilent上传文件
        String[] strings = storageClient.upload_file(
                "C:\\Users\\鹏哥\\Desktop\\照片\\韩花花\\韩花花mmexport1507901029855.jpg",
                "jpg",
                null);
        for (String string: strings) {
            System.out.println(string);
        }
    }
    @Test
    public void testFastDfsClient() throws Exception{
        FastDFSClient fastDFSClient = new FastDFSClient("E:\\IdeaWorpace\\e_Store\\e3_manager_web\\src\\main\\resources\\conf\\client.conf");
        String s = fastDFSClient.uploadFile("C:\\Users\\鹏哥\\Desktop\\照片\\韩花花\\韩花花mmexport1507127507770.jpg");
        System.out.println(s);
    }

    @Test
    public void testStringSplit(){
        String s = "123123213121312";
        String[] split = s.split(",");
        Long i = Long.valueOf(split[0]);
    }
}
