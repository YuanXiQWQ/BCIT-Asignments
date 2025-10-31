class Fraction{
private:
    int numerator;
    int denominator;

public:
public:

    Fraction(int numerator, int denominator){
        try{
            int res = numerator / denominator;
        } catch(std::exception &e){
            throw std::runtime_error("Denominator cannot be zero");
        }
    }
};
