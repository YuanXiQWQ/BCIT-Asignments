// GameControllerTest.java

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import javafx.application.Platform;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * JUnit test class for testing the GameController functionalities.
 * <p>w
 * This test class uses an inner MockMinecraftItem class to simulate actual UI behavior to
 * avoid NullPointerExceptions during testing.
 * <p>
 * Ensure all relevant classes (e.g., GameController, CraftingItem, Recipe, Item,
 * MaterialItem, etc.) are included in the classpath for the test.
 *
 * @author Jiarui Xing
 */
public class GameControllerTest {
    private GameController gameController;

    /**
     * Inner class to mock the behavior of MinecraftItem.
     */
    private static class MockMinecraftItem extends MinecraftItem {
        @Override
        public void updateUi()
        {
            // Simulate updateUi without performing any action
        }

        @Override
        public void displayFeedback(boolean isSuccess, boolean isFirstFailure)
        {
            // Simulate displayFeedback without performing any action
        }

        @Override
        public void askToPlayAgain()
        {
            // Simulate askToPlayAgain without performing any action
        }

        @Override
        public void start()
        {
            // No implementation needed
        }
    }

    /**
     * Initialize the JavaFX platform before all tests. This avoids IllegalStateException
     * when calling JavaFX methods during tests.
     *
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    @BeforeClass
    public static void initJfx() throws InterruptedException
    {
        final CountDownLatch latch = new CountDownLatch(1);
        // Start the JavaFX platform
        Platform.startup(latch::countDown);
        latch.await();
    }

    /**
     * Set up the test environment before each test. It creates a mock UI and initializes
     * the GameController instance.
     */
    @Before
    public void setUp()
    {
        // Create an instance of MockMinecraftItem
        MockMinecraftItem mockGameUi = new MockMinecraftItem();
        // Reset the GameController singleton instance
        resetGameControllerSingleton();
        // Initialize GameController
        gameController = GameController.getInstance(mockGameUi);
        gameController.startGame();
    }

    /**
     * Reset the singleton instance of GameController using reflection. This allows
     * reinitialization of the singleton instance for each test.
     */
    private void resetGameControllerSingleton()
    {
        try
        {
            java.lang.reflect.Field instanceField =
                    GameController.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch(NoSuchFieldException | IllegalAccessException e)
        {
            throw new RuntimeException(
                    "Failed to reset GameController singleton instance.", e);
        }
    }

    /**
     * Test the functionality of moving to the next question. Verifies that the remaining
     * question count decreases by one.
     */
    @Test
    public void testNextQuestion()
    {
        int initialRemaining = gameController.getRemainingQuestions();
        gameController.nextQuestion();
        assertEquals("Remaining questions should decrement by 1", initialRemaining - 1,
                gameController.getRemainingQuestions());
    }

    /**
     * Test placing an item on the crafting grid. Ensures that the item is correctly
     * placed at the expected position.
     */
    @Test
    public void testPlaceItemOnGrid()
    {
        // Place an item on the grid
        gameController.placeItemOnGrid(1, 1, "Diamond");
        // Verify that "Diamond" is correctly placed at position (0, 0) in playerRecipe
        Map<Recipe.Position, String> playerRecipe = gameController.getPlayerRecipe();
        assertEquals("Diamond should be placed at position (0, 0)", "Diamond",
                playerRecipe.get(new Recipe.Position(0, 0)));
    }

    /**
     * Test submitting a correct recipe. Verifies that the player's score increases when
     * the correct recipe is submitted.
     */
    @Test
    public void testSubmitCorrectRecipe()
    {
        // Simulate the correct recipe placement for the current item
        CraftingItem currentItem = gameController.getCurrentItem();
        Map<Recipe.Position, Set<String>> correctRecipe =
                currentItem.getRecipe().getRecipeMap();

        // Place the correct items on the grid
        for(Map.Entry<Recipe.Position, Set<String>> entry : correctRecipe.entrySet())
        {
            int gridX = entry.getKey().getX() + 1;
            int gridY = 1 - entry.getKey().getY();
            gameController.placeItemOnGrid(gridX, gridY, entry.getValue().toString());
        }

        // Capture the score before submitting the recipe
        int scoreBefore = gameController.getScore();

        // Submit the recipe
        gameController.submitRecipe();

        // Verify that the score has increased
        assertEquals("Score should increase after submitting a correct recipe",
                scoreBefore + 1, gameController.getScore());
    }

    /**
     * Test submitting an incorrect recipe. Verifies that the player's score does not
     * change when an incorrect recipe is submitted.
     */
    @Test
    public void testSubmitIncorrectRecipe()
    {
        // Place incorrect items on the grid
        gameController.placeItemOnGrid(1, 1, "IncorrectItem");

        // Capture the score before submitting the recipe
        int scoreBefore = gameController.getScore();

        // Submit the recipe
        gameController.submitRecipe();

        // Verify that the score remains the same
        assertEquals("Score should not change after submitting an incorrect recipe",
                scoreBefore, gameController.getScore());
    }

    /**
     * Test skipping a question. Verifies that the remaining question count decreases by
     * one after skipping.
     */
    @Test
    public void testSkipQuestion()
    {
        int initialRemaining = gameController.getRemainingQuestions();
        gameController.skipQuestion();
        assertEquals("Remaining questions should decrement by 1 after skipping",
                initialRemaining - 1, gameController.getRemainingQuestions());
    }

    /**
     * Test the end of the game after all questions have been answered. Verifies that no
     * remaining questions are left.
     */
    @Test
    public void testGameEndsAfterAllQuestions()
    {
        // Answer all questions
        for(int i = 0; i < gameController.getTotalQuestions(); i++)
        {
            gameController.nextQuestion();
        }
        assertEquals("No remaining questions should be left", 0,
                gameController.getRemainingQuestions());
    }

    /**
     * Test the generation of option items. Verifies that the option items list is not
     * null and contains between 1 and 9 items.
     */
    @Test
    public void testOptionItemsAreGenerated()
    {
        List<Item> optionItems = gameController.getOptionItems();
        assertNotNull("Option items should not be null", optionItems);
        assertTrue("Option items should contain between 1 and 9 items",
                !optionItems.isEmpty() && optionItems.size() <= 9);
    }

    /**
     * Test retrieving the current crafting item. Verifies that the current item and its
     * recipe are not null.
     */
    @Test
    public void testGetCurrentItem()
    {
        CraftingItem currentItem = gameController.getCurrentItem();
        assertNotNull("Current item should not be null", currentItem);
        assertNotNull("Current item's recipe should not be null",
                currentItem.getRecipe());
    }

    /**
     * Test generating transformed recipes. Verifies that the transformations of a recipe
     * are valid and non-empty.
     */
    @Test
    public void testRecipeTransformation()
    {
        // Test the recipe transformations for a known recipe
        CraftingItem currentItem = gameController.getCurrentItem();
        Recipe originalRecipe = currentItem.getRecipe();
        List<Map<Recipe.Position, Set<String>>> transformedRecipes =
                gameController.generateTransformedRecipes(originalRecipe.getRecipeMap());

        assertNotNull("Transformed recipes should not be null", transformedRecipes);
        assertFalse("There should be at least one transformed recipe",
                transformedRecipes.isEmpty());
    }
}
