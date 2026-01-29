namespace ChartDemo.Models
{
    public class MonthlyCategorySalesViewModel
    {
        public string Month { get; init; } = "";
        public int Electronics { get; init; }
        public int Clothing { get; init; }
        public int Home { get; init; }
    }
}