import java.util.Scanner;

public class ConsoleInterface {
    public Scanner scan = new Scanner(System.in);

    // Меню выбора действий
    public void runMenu() {
        boolean start = true;

        while (start == true) {
            System.out.println("\n--- РАБОТА С МАТРИЦАМИ ---");
            System.out.println("1. Сложить (+)");
            System.out.println("2. Умножить (*)");
            System.out.println("3. Разделить (/)");
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
                System.out.println("\nМатрица 1:");
                Matrix m1 = buildMatrix();
                System.out.println("\nМатрица 2:");
                Matrix m2 = buildMatrix();

                System.out.println("\nИсходные матрицы:");
                m1.display();
                System.out.println();
                m2.display();

                System.out.println("\nРезультат:");
                Matrix res = m1.divideMatrices(m2);
                res.display();
                anychoice = true;
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
        System.out.print("Укажите число строк: ");
        int rows = scan.nextInt();
        System.out.print("Укажите число столбцов: ");
        int cols = scan.nextInt();

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
                        System.out.print("Ячейка [" + i + "][" + j + "] Действительная часть: ");
                        double r = scan.nextDouble();
                        System.out.print("              Мнимая часть: ");
                        double im = scan.nextDouble();

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
}