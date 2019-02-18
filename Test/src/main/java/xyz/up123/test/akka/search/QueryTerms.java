package xyz.up123.test.akka.search;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.akka.search
 * @ClassName: QueryTerms
 * @Description: 定义查询条件类，用于传递消息
 * @Author: ershixiong
 * @CreateDate: 2019/2/15 20:03
 * @Version: 1.0
 */
public class QueryTerms {
    /**
     * 问题
     */
    private String question;
    /**
     * 搜索引擎
     */
    private String engine;

    public QueryTerms(String question, String engine) {
        this.question = question;
        this.engine = engine;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    @Override
    public String toString() {
        return "QueryTerms{" +
                "question='" + question + '\'' +
                ", engine='" + engine + '\'' +
                '}';
    }
}
