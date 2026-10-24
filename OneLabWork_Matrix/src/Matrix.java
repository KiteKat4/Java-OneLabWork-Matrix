import java.util.Random;

public class Matrix {
    public int rowCount;
    public int colCount;
    public ComplexNumbers[][] arr;

    // Конструктор создания матрицы (сетки с ячейками)
    public Matrix(int r, int c) {
        this.rowCount = r;
        this.colCount = c;
        this.arr = new ComplexNumbers[r][c];
        for (int i = 0; i < r; ++i) {
            for (int j = 0; j < c; ++j) {
                this.arr[i][j] = new ComplexNumbers();
            }
        }
    }

    // Сложение матриц
    public Matrix addMatrices(Matrix other) {
        if (this.rowCount != other.rowCount || this.colCount != other.colCount) {
            System.out.println("Ошибка: Размеры матриц не совпадают!");
            return new Matrix(0, 0);
        }
        Matrix resMatrix = new Matrix(this.rowCount, this.colCount);
        for (int i = 0; i < this.rowCount; ++i) {
            for (int j = 0; j < this.colCount; ++j) {
                resMatrix.arr[i][j] = this.arr[i][j].add(other.arr[i][j]);
            }
        }
        return resMatrix;
    }

    // Умножение матриц
    public Matrix multiplyMatrices(Matrix other) {
        if (this.colCount != other.rowCount) {
            System.out.println("Ошибка: Матрицы несогласованы (кол-во столбцов первой матрицы не равно кол-ву строк второй матрицы)!");
            return new Matrix(0, 0);
        }
        Matrix resMatrix = new Matrix(this.rowCount, other.colCount);
        for (int i = 0; i < this.rowCount; ++i) {
            for (int j = 0; j < other.colCount; ++j) {
                ComplexNumbers sum = new ComplexNumbers(0, 0);
                for (int k = 0; k < this.colCount; ++k) {
                    ComplexNumbers step = this.arr[i][k].multiply(other.arr[k][j]);
                    sum = sum.add(step);
                }
                resMatrix.arr[i][j] = sum;
            }
        }
        return resMatrix;
    }

    // Поэлементное деление
    public Matrix divideMatrices(Matrix other, boolean rightDivision) {
        ComplexNumbers det = other.calcDeterminant();
        Matrix ObratMatrix = new Matrix(other.rowCount, other.colCount);
        Matrix SouzMatrix = other.createSouzMatrix();
        ComplexNumbers one = new ComplexNumbers(1.0, 0.0);
        ComplexNumbers InvDet = one.divideSoprChislo(det); // Деление 1/det(B). Матрица B - это other

        for (int i = 0; i < SouzMatrix.rowCount; ++i){
            for (int j = 0; j < SouzMatrix.colCount; ++j){
                ObratMatrix.arr[i][j] = InvDet.multiply(SouzMatrix.arr[i][j]);
            }
        }

        if (rightDivision == true) {
            return this.multiplyMatrices(ObratMatrix); // Правое деление: A * B⁻¹
        }
        else {
            return ObratMatrix.multiplyMatrices(this); // Левое деление: B⁻¹ * A
        }
    }

    // Транспонирование
    public Matrix transposeMatrix() {
        Matrix resMatrix = new Matrix(this.colCount, this.rowCount);
        for (int i = 0; i < this.rowCount; ++i) {
            for (int j = 0; j < this.colCount; ++j) {
                resMatrix.arr[j][i] = this.arr[i][j];
            }
        }
        return resMatrix;
    }

    // Вычисление определителя
    public ComplexNumbers calcDeterminant() {
        if (this.rowCount != this.colCount) {
            System.out.println("Ошибка: Матрица не квадратная!");
            return new ComplexNumbers();
        }
        return runDeterminant(this.arr, this.rowCount);
    }

    // Вспомогательный метод (само вычисление)
    private ComplexNumbers runDeterminant(ComplexNumbers[][] currentArr, int size) {
        // Рекурсия (метод Лапласа-разложение по первой строке)
        if (size == 1) {
            return currentArr[0][0];
        }
        if (size == 2) {
            ComplexNumbers p1 = currentArr[0][0].multiply(currentArr[1][1]);
            ComplexNumbers p2 = currentArr[0][1].multiply(currentArr[1][0]);
            ComplexNumbers diff = p1.subtract(p2);
            return diff;
        }

        ComplexNumbers total = new ComplexNumbers(0, 0);
        for (int i = 0; i < size; ++i) {
            ComplexNumbers[][] smallArr = new ComplexNumbers[size - 1][size - 1];
            for (int r = 1; r < size; ++r) {
                int targetCol = 0;
                for (int c = 0; c < size; ++c) {
                    if (c != i) {
                        smallArr[r - 1][targetCol] = currentArr[r][c];
                        targetCol = targetCol + 1;
                    }
                }
            }

            ComplexNumbers sign;
            if (i % 2 == 0) {
                sign = new ComplexNumbers(1, 0);
            } else {
                sign = new ComplexNumbers(-1, 0);
            }

            ComplexNumbers minor = runDeterminant(smallArr, size - 1);
            ComplexNumbers part = currentArr[0][i].multiply(sign).multiply(minor);
            total = total.add(part);
        }
        return total;
    }

    // Создание союзной (присоединённой) матрицы
    public Matrix createSouzMatrix() {
        Matrix Souz = new Matrix(this.rowCount, this.colCount);

        // Находим алгебраические дополнения для каждого элемента [i][j]
        for (int i = 0; i < this.rowCount; i++) {
            for (int j = 0; j < this.colCount; j++) {

                // Создаем массив для минора (на 1 меньше текущего размера)
                int size = this.rowCount;
                ComplexNumbers[][] smallArr = new ComplexNumbers[size - 1][size - 1];

                int targetRow = 0;
                for (int r = 0; r < size; ++r) {
                    if (r == i) continue; // Вычеркиваем строку i

                    int targetCol = 0;
                    for (int c = 0; c < size; ++c) {
                        if (c == j) continue; // Вычеркиваем столбец j
                        smallArr[targetRow][targetCol] = this.arr[r][c];
                        ++targetCol;
                    }
                    ++targetRow;
                }

                ComplexNumbers minorDet = runDeterminant(smallArr, size - 1); // Считаем определитель минора

                // Учитываем знак (-1)^(i+j)
                ComplexNumbers sign;
                if ((i + j) % 2 == 0) {
                    sign = new ComplexNumbers(1, 0);
                } else {
                    sign = new ComplexNumbers(-1, 0);
                }

                Souz.arr[i][j] = sign.multiply(minorDet); // Алгебраическое дополнение = знак * определитель минора
            }
        }

        // Союзная матрица (транспонированная матрица дополнений)
        return Souz.transposeMatrix();
    }

    // Заполнение матрицы рандомными числами
    public Matrix BuildRandomMatrix(double min, double max){
        Random random = new Random();

        for (int i = 0; i < this.rowCount; ++i) {
            for (int j = 0; j < this.colCount; ++j) {
                // Создание случайного вещественного числа в диапазоне от min до max
                double randomReal = min + (max - min) * random.nextDouble(); // random.nextDouble() от 0.0 до 1.0 (не включит)
                double randomImag = min + (max - min) * random.nextDouble(); // нужно прибавлять min чтобы сдивагать диапазон и было от -500 до 500 примерно

                // Округление до 1 знака после запятой
                randomReal = Math.round(randomReal * 10.0) / 10.0; // Math.round может принимать веществ. числа, но возвращает всегда long для double или int для float
                randomImag = Math.round(randomImag * 10.0) / 10.0;

                this.arr[i][j] = new ComplexNumbers(randomReal, randomImag);
            }
        }
        return this;
    }

    // Вывод матрицы (массива) на экран
    public void display() {
        for (int i = 0; i < this.rowCount; i++) {
            System.out.print("[ ");
            for (int j = 0; j < this.colCount; j++) {
                this.arr[i][j].show();
                System.out.print("    ");
            }
            System.out.println(" ]");
        }
    }
}
