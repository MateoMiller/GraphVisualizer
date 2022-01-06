package Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {
    public MainWindow() {
        JMenuBar menu = new JMenuBar();
        InitGraphSettingFunctions(menu);
        InitOperations(menu);
        InitAlgorithms(menu);
        this.setJMenuBar(menu);

        //Чтобы приложение занимало 75% экрана и было в середине экрана
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(Math.round(dim.width * 0.75f), Math.round(dim.height * 0.75f));
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    private void InitGraphSettingFunctions(JMenuBar menu) {
        JMenu settingsMenu = new JMenu("Таблицы переходов");

        JMenuItem setGraphFromTransitionTable = new JMenuItem(
                new AbstractAction("Задать автомат по таблице переходов") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Я задаю автомат");
                    }
                }
        );

        JMenuItem generateTransitionTable = new JMenuItem(
                new AbstractAction("Сгенерировать таблицу переходов") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Я генерирую таблицу переходов");
                    }
                });

        settingsMenu.add(setGraphFromTransitionTable);
        settingsMenu.add(generateTransitionTable);

        menu.add(settingsMenu);
    }

    private void InitOperations(JMenuBar menu) {
        JMenu operationsMenu = new JMenu("Операция");

        JMenuItem addNodeItem = new JMenuItem(
                new AbstractAction("Добавить вершину") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Добавляю вершину");
                    }
                });

        JMenuItem removeNodeItem = new JMenuItem(
                new AbstractAction("Удалить вершину") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Удаляю вершину");
                    }
                });
        JMenuItem addEdgeItem = new JMenuItem(
                new AbstractAction("Добавить ребро") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Добавляю ребро");
                    }
                });
        JMenuItem removeEdgeItem = new JMenuItem(
                new AbstractAction("Удалить ребро") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Удаляю ребро");
                    }
                });

        operationsMenu.add(addNodeItem);
        operationsMenu.add(removeNodeItem);
        operationsMenu.add(addEdgeItem);
        operationsMenu.add(removeEdgeItem);

        menu.add(operationsMenu);
    }

    private void InitAlgorithms(JMenuBar menu) {
        JMenu algorithmMenu = new JMenu("Алгоритм");

        JMenuItem bfsItem = new JMenuItem(
                new AbstractAction("Поиск в глубину") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Начинаю поиск в глубину");
                    }
                });
        JMenuItem dfsItem = new JMenuItem(
                new AbstractAction("Поиск в ширину") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Начинаю поиск в ширину");
                    }
                });

        JMenuItem determiningItem = new JMenuItem(
                new AbstractAction("Преобразовать в ДКА") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Преобразую в ДКА");
                    }
                }
        );

        algorithmMenu.add(bfsItem);
        algorithmMenu.add(dfsItem);
        algorithmMenu.add(determiningItem);

        menu.add(algorithmMenu);
    }
}
