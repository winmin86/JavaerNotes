package xyz.up123.test.akka.search;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.akka.search
 * @ClassName: QueryResult
 * @Description: 定义查询结果类，用于消息传递
 * @Author: ershixiong
 * @CreateDate: 2019/2/15 20:04
 * @Version: 1.0
 */
public class QueryResult {
    /**
     * 查询结果
     */
    private String result;

    public QueryResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "result='" + result + '\'' +
                '}';
    }
}
