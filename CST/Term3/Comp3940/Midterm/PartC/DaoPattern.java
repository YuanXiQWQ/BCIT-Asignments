import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;
// 创建一个抽象基类，包含一个抽象的 serialize 方法，供子类实现
abstract class Game {
    String GameType;
    public Game(String gameType) { this.GameType = gameType; }
    abstract String serialize();
}
// Quiz 类继承自抽象类 Game
class Quiz extends Game {
    int Id;
    String Question;
    String ContentPath;
    public Quiz() { super("Quiz"); } // 默认构造函数
    public Quiz(String constructorParams) { // 该构造函数解析 constructorParams 来设置对象状态
        super("Quiz");
        String[] keyvaluePairs = constructorParams.split(",");
        for(int i = 0; i < keyvaluePairs.length; i++) {
            String[] keyvaluePair = keyvaluePairs[i].split("=");
            switch (keyvaluePair[0]) {
                case "Id": this.Id = Integer.parseInt(keyvaluePair[1]); break;
                case "Question": this.Question = keyvaluePair[1]; break;
                case "ContentPath": this.ContentPath = keyvaluePair[1];break;
            }
        }
    }
    void setId(int id) { this.Id = id; }
    void setQuestion (String question) { this.Question = question; }
    void setContentPath (String contentPath) { this.ContentPath = contentPath; }
    // Quiz 对 serialize() 方法的实现
    @Override
    String serialize() { return "Id="+this.Id+",Question="+this.Question+",ContentPath="+this.ContentPath; }
}
// 另一个继承自抽象类 Game 的类
class Puzzle extends Game {
    int Id;
    String Name;
    String Details;
    public Puzzle() { super("Puzzle"); }
    public Puzzle(String constructorParams) { super("Puzzle"); } // 当前未实现具体逻辑
    void setId(int id) { this.Id = id; }
    void setName(String name) { this.Name = name; }
    void setDetails(String details) { this.Details = details; }
    // Puzzle 对 serialize() 方法的实现
    @Override
    String serialize() { return "Id="+this.Id+",Name="+this.Name+",Details="+this.Details; }
}
// 工厂类示例
// GameFactory 将被 Repository 使用，用于创建不同类型的 Game 对象实例
class GameFactory {
    public Game createGame(String gameType, String constructorParameters) {
        if (gameType.equalsIgnoreCase("Quiz")) {
            return new Quiz(constructorParameters);
        } else if (gameType.equalsIgnoreCase("Puzzle")) {
            return new Puzzle(constructorParameters);
        }
        return null;
    }
}
// IRepository 接口
// 接口允许实现基于组件的架构和设计
interface IRepository
{
    public List<Game> select(String gameType, String criteria); // 使用 criteria 构建 Where 子句并反序列化每个游戏对象
    public void insert(Game game);  // 反序列化游戏对象并通过 JDBC 插入数据库
}
// Repository 类实现 IRepository 接口
// 注意：Repository 类可以管理 Game 的任意子类对象
// 这是通过继承机制与工厂模式实现的
class Repository implements IRepository {
    List<Game> gameList = null;
    public Repository() { gameList = new ArrayList<Game>(); }
    @Override
    public void insert(Game game) { gameList.add(game) ; }
    @Override
    public List<Game> select(String gameType, String pattern) {   // 注意该方法中函数式编程的使用
        Stream<Game> gameStream = gameList.stream();
        Stream<Game> newGameStream = gameStream.filter(s -> s.GameType == gameType && s.serialize().contains(pattern));
        List<Game> selectedGames = new ArrayList<Game>();
        GameFactory gameFactory = new GameFactory();
        newGameStream.forEach(s -> selectedGames.add(gameFactory.createGame(s.GameType, s.serialize())));
        return selectedGames;
    }
}
public class DaoPattern
{
    public static void main(String[] args) {
        // repository 对象应在 Servlet 的构造函数中创建
        IRepository repository = new Repository();
        // 在 doPost 或 doPut 方法中，从 request 对象提取值后，创建 quiz 对象并通过 Repository 插入数据库
        Quiz quiz = new Quiz(); quiz.setId(1);
        quiz.setQuestion("Who is the Governal General of Canada");
        quiz.setContentPath("youtube.com/xyz");
        repository.insert(quiz);
        // 另一个 doPost 或 doPut 请求，将另一个 quiz 对象插入数据库
        quiz = new Quiz(); quiz.setId(2);
        quiz.setQuestion("Who is the Prime Minister of Canada");
        quiz.setContentPath("youtube.com/abc");
        repository.insert(quiz);
        // 仅展示上述设计如何使 Repository 能够管理任何 Game 子类对象
        Puzzle puzzle = new Puzzle(); puzzle.setId(1);
        puzzle.setName("jigsaw");
        puzzle.setDetails("quite difficult");
        repository.insert(puzzle);
        // 在 doGet 方法中从 Repository 中选择一些 quiz（或多个）对象
        List<Game> selectedGames = repository.select("Quiz", "Id=2");
        for (Game game : selectedGames) {
            System.out.println(game.serialize());
        }
    }
}
