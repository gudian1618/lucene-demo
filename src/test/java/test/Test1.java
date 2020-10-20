package test;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author gudian1618
 * @version v1.0
 * @date 2020/10/20 10:38 下午
 */

public class Test1 {
    String[] a = {
        "3, 华为 - 华为电脑, 爆款",
        "4, 华为手机, 旗舰",
        "5, 联想 - ThinkPad, 商务本",
        "6, 联想手机, 自拍神器"
    };

    @Test
    public void test1() throws IOException {
        // 准备目录
        File path = new File("src/test/java/test");
        FSDirectory d = FSDirectory.open(path.toPath());

        // 配置中文分词器
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        IndexWriterConfig cfg = new IndexWriterConfig(analyzer);

        // 索引输出工具
        IndexWriter writer = new IndexWriter(d, cfg);

        for (int i = 0; i < a.length; i++) {
            String s = a[i];
            String[] b = s.split(",");

            // 新建Document实例,封装id和内容两个字段的数据
            Document doc = new Document();
            // 生成索引,可以用id值从索引中查询
            // LongPoint类型不会自动存储
            doc.add(new LongPoint("id", Long.parseLong(b[0])));
            // 单独设置LongPoint存储到文档
            doc.add(new StoredField("id", Long.parseLong(b[0])));
            doc.add(new TextField("title", b[1], Field.Store.YES));
            doc.add(new TextField("sellPoint", b[2], Field.Store.YES));

            // 用索引输出工具,来输出这一篇文档
            writer.addDocument(doc);
        }
        writer.close();
    }
}
