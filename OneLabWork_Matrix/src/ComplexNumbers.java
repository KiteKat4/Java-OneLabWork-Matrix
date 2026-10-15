public class ComplexNumbers {
    public double realPart;
    public double imagPart;

    // Конструктор создания комплексного числа по умолчанию
    public ComplexNumbers() {
        this.realPart = 0.0;
        this.imagPart = 0.0;
    }

    // Конструктор создания комплексного числа с параметрами
    public ComplexNumbers(double r, double i) {
        this.realPart = r;
        this.imagPart = i;
    }

    // Сложение чисел
    public ComplexNumbers add(ComplexNumbers number) {
        ComplexNumbers result = new ComplexNumbers();
        result.realPart = this.realPart + number.realPart;
        result.imagPart = this.imagPart + number.imagPart;
        return result;
    }

    // Вычитание чисел
    public ComplexNumbers subtract(ComplexNumbers number) {
        ComplexNumbers result = new ComplexNumbers();
        result.realPart = this.realPart - number.realPart;
        result.imagPart = this.imagPart - number.imagPart;
        return result;
    }

    // Умножение чисел
    public ComplexNumbers multiply(ComplexNumbers number) {
        ComplexNumbers result = new ComplexNumbers();
        result.realPart = this.realPart * number.realPart - this.imagPart * number.imagPart;
        result.imagPart = this.realPart * number.imagPart + this.imagPart * number.realPart;
        return result;
    }

    // Деление через сопряженное число
    public ComplexNumbers divide(ComplexNumbers number) {
        ComplexNumbers znak = new ComplexNumbers();
        znak.realPart = number.realPart;
        znak.imagPart = number.imagPart * (-1.0);

        ComplexNumbers chislit = this.multiply(znak);
        ComplexNumbers bottom = number.multiply(znak);
        double divValue = bottom.realPart;

        ComplexNumbers result = new ComplexNumbers();
        if (divValue != 0.0) {
            result.realPart = chislit.realPart / divValue;
            result.imagPart = chislit.imagPart / divValue;
        } else {
            System.out.println("Ошибка: Деление на ноль!");
        }
        return result;
    }

    public void show() {
        if (this.imagPart >= 0.0) {
            System.out.printf("%7.1f + %6.1fi", this.realPart, this.imagPart);
        } else {
            // Если мнимая часть меньше нуля, то Math.abs, чтобы убрать двойной минус мне
            System.out.printf("%7.1f - %6.1fi", this.realPart, Math.abs(this.imagPart));
        }
    }

}

