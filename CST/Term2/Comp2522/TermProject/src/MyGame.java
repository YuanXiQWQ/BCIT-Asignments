import java.util.Scanner;

/**
 * @author YuanXi
 */
public class MyGame implements Game {
    private final Scanner scanner;

    public MyGame()
    {
        scanner = new Scanner(System.in);
    }

    @Override
    public void start()
    {
        // 示例游戏：简单的数学谜题
        System.out.println("欢迎来到我的游戏！这是一个简单的数学谜题游戏。");
        System.out.println("你需要解答5个数学问题。每个问题有两个选项，选择正确的答案。");

        int score = 0;
        for(int i = 1; i <= 5; i++)
        {
            MathQuestion question = generateQuestion();
            System.out.println("问题 " + i + ": " + question.getQuestion());
            System.out.println("A. " + question.getOptionA());
            System.out.println("B. " + question.getOptionB());

            String answer = "";
            boolean valid = false;
            while(!valid)
            {
                System.out.print("请选择 A 或 B: ");
                answer = scanner.nextLine().trim().toLowerCase();
                if("a".equals(answer) || "b".equals(answer))
                {
                    valid = true;
                } else
                {
                    System.out.println("输入无效，请选择 A 或 B。");
                }
            }

            if(answer.equals(question.getCorrectOption()))
            {
                System.out.println("正确！");
                score += 1;
            } else
            {
                System.out.println(
                        "错误。正确答案是 " + question.getCorrectOption().toUpperCase() +
                                ".");
            }
        }

        System.out.println("游戏结束。你的得分是 " + score + " / 5。");
    }

    private MathQuestion generateQuestion()
    {
        // 简单的数学问题生成器
        int a = (int) (Math.random() * 10) + 1;
        int b = (int) (Math.random() * 10) + 1;
        String question = "What is " + a + " + " + b + "?";
        int correctAnswer = a + b;

        // 生成一个错误答案
        int wrongAnswer = correctAnswer + (int) (Math.random() * 5) + 1;

        // 随机决定选项位置
        if(Math.random() < 0.5)
        {
            return new MathQuestion(question, String.valueOf(correctAnswer),
                    String.valueOf(wrongAnswer), "a");
        } else
        {
            return new MathQuestion(question, String.valueOf(wrongAnswer),
                    String.valueOf(correctAnswer), "b");
        }
    }

    // 内部类：数学问题
    private static class MathQuestion {
        private final String question;
        private final String optionA;
        private final String optionB;
        private final String correctOption;

        public MathQuestion(String question, String optionA, String optionB,
                            String correctOption)
        {
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.correctOption = correctOption;
        }

        public String getQuestion()
        {
            return question;
        }

        public String getOptionA()
        {
            return optionA;
        }

        public String getOptionB()
        {
            return optionB;
        }

        public String getCorrectOption()
        {
            return correctOption;
        }
    }
}
