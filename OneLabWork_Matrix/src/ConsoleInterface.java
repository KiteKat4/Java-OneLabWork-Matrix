import java.util.Scanner;
import java.util.Locale;

public class ConsoleInterface {
    public Scanner scan = new Scanner(System.in).useLocale(Locale.US);

    // Меню выбора действий
    public void runMenu() {
        boolean start = true;

        while (start == true) {
            System.out.println("\n--- РАБОТА С МАТРИЦАМИ ---");
            System.out.println("1. Сложить (+)");
            System.out.println("2. Умножить (*)");
            System.out.println("3. Разделить матрицу A на B (B - обратная)(Будет выбор: правое деление A*B⁻¹ или левое деление B⁻¹*A)");
            System.out.println("4. Транспонировать");
            System.out.println("5. Найти определитель");
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");

            String action = scan.next();
            boolean anychoice = false;

            if (action.equals("0")) {
                System.out.println("Выход из программы.");
                break;
            } else if (action.equals("1")) {
                System.out.println("\nМатрица 1:");
                Matrix m1 = buildMatrix();
                System.out.println("\nМатрица 2:");
                Matrix m2 = buildMatrix();

                System.out.println("\nИсходные матрицы:");
                m1.display();
                System.out.println();
                m2.display();

                System.out.println("\nРезультат:");
                Matrix res = m1.addMatrices(m2);
                res.display();
                anychoice = true;
            } else if (action.equals("2")) {
                System.out.println("\nМатрица 1:");
                Matrix m1 = buildMatrix();
                System.out.println("\nМатрица 2:");
                Matrix m2 = buildMatrix();

                System.out.println("\nИсходные матрицы:");
                m1.display();
                System.out.println();
                m2.display();

                System.out.println("\nРезультат:");
                Matrix res = m1.multiplyMatrices(m2);
                res.display();
                anychoice = true;
            } else if (action.equals("3")) {
                System.out.println("\nМатрица A:");
                Matrix m1 = buildMatrix();
                System.out.println("\nМатрица B (Должна быть квадратной!):");
                Matrix m2 = buildMatrix();

                System.out.println("\nИсходные матрицы:");
                m1.display();
                System.out.println();
                m2.display();

                // Первая проверка: Квадратная ли матрица B ?
                if (m2.colCount != m2.rowCount) {
                    System.out.println("\nРезультат:");
                    System.out.println("Ошибка: Матрица-делитель(B) не квадратная!");
                    anychoice = true;
                }
                // Вторая проверка: Считаем определитель квадратной матрицы B и проверяем на 0
                else if (m2.calcDeterminant().realPart == 0.0 && m2.calcDeterminant().imagPart == 0.0) {
                    System.out.println("\nРезультат:");
                    System.out.println("Ошибка: Матрица-делитель(B) должна быть невырожденной (det(B) != 0) !");
                    anychoice = true;
                }
                // Главный блок: Выполняется только если матрица B идеальна (квадратная и det != 0)
                else {
                    if ((m1.colCount == m2.rowCount) && (m2.colCount == m1.rowCount)) {
                        System.out.println("\nПрограмма выведет правое деление A*B⁻¹ и левое деление B⁻¹*A");
                        System.out.println("\nРезультат:");

                        // Считаем правое деление (передаем true)
                        Matrix rightRes = m1.divideMatrices(m2, true);
                        System.out.println("Правое деление A*B⁻¹:");
                        rightRes.display();

                        // Считаем левое деление (передаем false)
                        Matrix leftRes = m1.divideMatrices(m2, false);
                        System.out.println("\nЛевое деление B⁻¹*A:");
                        leftRes.display();
                    }
                    else if (m1.colCount == m2.rowCount) {
                        Matrix rightRes = m1.divideMatrices(m2, true);
                        System.out.println("\nПрограмма выведет только правое деление A*B⁻¹");
                        System.out.println("Т.к для левого деления B⁻¹*A кол-во столбцов матрицы B⁻¹ (" + m2.colCount + ") не совпадает с кол-вом строк матрицы A (" + m1.rowCount + ")");
                        System.out.println("\nРезультат:");
                        System.out.println("Правое деление A*B⁻¹:");
                        rightRes.display();
                    }
                    else if (m2.colCount == m1.rowCount) {
                        Matrix leftRes = m1.divideMatrices(m2, false);
                        System.out.println("\nПрограмма выведет только левое деление B⁻¹*A");
                        System.out.println("Т.к для правого деления A*B⁻¹ кол-во столбцов матрицы A (" + m1.colCount + ") не совпадает с кол-вом строк матрицы B⁻¹ (" + m2.rowCount + ")");
                        System.out.println("\nРезультат:");
                        System.out.println("Левое деление B⁻¹*A:");
                        leftRes.display();
                    }

                    anychoice = true; // Ставим true в конце успешных вычислений
                }

            } else if (action.equals("4")) {
                Matrix m = buildMatrix();
                System.out.println("\nИсходная матрица:");
                m.display();

                System.out.println("\nРезультат:");
                Matrix res = m.transposeMatrix();
                res.display();
                anychoice = true;
            } else if (action.equals("5")) {
                Matrix m = buildMatrix();
                System.out.println("\nИсходная матрица:");
                m.display();

                ComplexNumbers det = m.calcDeterminant();
                if (m.rowCount == m.colCount) {
                    System.out.print("\nОпределитель = ");
                    det.show();
                    System.out.println();
                }
                anychoice = true;
            } else {
                System.out.println("\nНеверный пункт.");
            }

            if (anychoice == true) {
                System.out.println("\nПовторить запуск программы ? \n[+]-запустить/[любая другая клавиша]-завершить: ");
                String choice = scan.next();
                if (!choice.equals("+")) {
                    System.out.println("Выход из программы.");
                    break;
                }
            }
        }
    }

    // Ввод матрицы
    public Matrix buildMatrix() {
        int rows = IntegerChecking(scan, "Укажите число строк: ");
        int cols = IntegerChecking(scan, "Укажите число столбцов: ");

        Matrix massive = new Matrix(rows, cols);

        boolean start = true;

        while (start == true) {
            System.out.println("\nЗаполнить матрицу рандомными числами (в диапазоне от -500.0 до 500.0) или своими ? \n[1]-рандомно/[2]-своими значениями: ");
            String choice = scan.next();

            if (choice.equals("1")) {
                return massive.BuildRandomMatrix(-500.0, 500.0);
            }
            else if (choice.equals("2")) {
                for (int i = 0; i < rows; ++i) {
                    for (int j = 0; j < cols; ++j) {
                        double r = RealChecking(scan, "Ячейка [" + i + "][" + j + "] Действительная часть: ");
                        double im = RealChecking(scan, "              Мнимая часть: ");
                        massive.arr[i][j] = new ComplexNumbers(r, im);
                    }
                }
                break;
            }
            else {
                System.out.println("\nНеверный пункт! Повторите ввод.");
            }
        }
        return massive;
    }

    public static double RealChecking (Scanner scan, String message){
        double number = 0.0;
        boolean flag = true;
        System.out.print(message);

        while (flag){
            String input = scan.next();
            String ReplacedNumber = input.replace(",", ".");
            try {
                // Проверка: Если строка НЕ состоит ТОЛЬКО из цифр, одной точки или минуса
                if (!ReplacedNumber.matches("-?\\d+(\\.\\d+)?") && !ReplacedNumber.matches("-?\\d+")) {
                    throw new NumberFormatException(); // Искусственно вызываем ошибку формата
                }

                number = Double.parseDouble(ReplacedNumber);
                flag = false;
            }
            catch (NumberFormatException e){
                System.out.println("Некорректный ввод! Повторите попытку. Введите число!\n");
                System.out.print(message);
            }
        }
        return number;
    }

    public static int IntegerChecking (Scanner scan, String message){
        int number = 0;
        boolean flag = true;
        System.out.print(message);

        while (flag){
            String input = scan.next();
            try {
                number = Integer.parseInt(input);
                if (number > 0){
                    flag = false;
                }
                else{
                    throw new NumberFormatException();
                }
            }
            catch (NumberFormatException e){
                System.out.println("Некорректный ввод! Повторите попытку. Введите натуральное число!\n");
                System.out.print(message);
            }
        }
        return number;
    }
}
