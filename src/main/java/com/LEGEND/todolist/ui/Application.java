package com.LEGEND.todolist.ui;

import com.LEGEND.todolist.domain.Todo;
import com.LEGEND.todolist.service.UserService;
import com.LEGEND.todolist.service.TodoService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Application {
    private final UserService userService;
    private Scanner scanner;
    private TodoService todoService;
    private String loggedInUser;

    public Application() {
        this.userService = new UserService();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("\n===== TODO 리스트 프로그램 =====");
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("9. 프로그램 종료");
            System.out.print("메뉴 선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> registerUser();
                    case 2 -> loginUser();
                    case 9 -> {
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    }
                    default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                }
            } catch (Exception e) {
                System.out.println("오류: " + e.getMessage());
            }
        }
    }

    private void registerUser() {
        System.out.print("아이디 입력: ");
        String id = scanner.nextLine();

        System.out.print("비밀번호 입력: ");
        String password = scanner.nextLine();

        if (userService.register(id, password)) {
            System.out.println("회원가입 성공: " + id);
        } else {
            System.out.println("이미 존재하는 아이디입니다.");
        }
    }

    private void loginUser() {
        System.out.print("아이디 입력: ");
        String id = scanner.nextLine();

        System.out.print("비밀번호 입력: ");
        String password = scanner.nextLine();

        if (userService.login(id, password)) {
            System.out.println("로그인 성공! TODO 리스트 관리로 이동합니다.");
            loggedInUser = id;
            todoService = new TodoService(loggedInUser);
            manageTodoList();
        } else {
            System.out.println("로그인 실패. 아이디 또는 비밀번호를 확인하세요.");
        }
    }

    private void manageTodoList() {
        while (true) {
            System.out.println("\n===== TODO 리스트 관리 =====");
            System.out.println("1. 오늘의 할 일 조회");
            System.out.println("2. 모든 할 일 조회");
            System.out.println("3. 할 일 추가");
            System.out.println("4. 완료 처리");
            System.out.println("5. 할일 수정");
            System.out.println("6. 할 일 삭제");
            System.out.println("9. 프로그램 종료");
            System.out.print("메뉴 선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showTodayTodos();
                case 2 -> showAllTodos();
                case 3 -> addTodo();
                case 4 -> completeTodo();
                case 5 -> editTodo();
                case 6 -> removeTodo();
                case 9 -> {
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                }
                default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    public void showTodayTodos() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        System.out.println("\n========================");
        System.out.println("      오늘의 할 일   ");
        System.out.println("========================");
        System.out.println("날짜: " + today);
        System.out.println("------------------------");

        List<Todo> todayTodos = todoService.getTodayTodos();

        if (todayTodos.isEmpty()) {
            System.out.println("오늘의 할 일이 없습니다.");
        } else {
            for (int i = 0; i < todayTodos.size(); i++) {
                System.out.printf("%d. %s\n", (i + 1), todayTodos.get(i));
            }
        }
        System.out.println("========================\n");
    }



    private void showAllTodos() {
        System.out.println("\n===== 모든 할 일 목록 =====");
        todoService.displayTodos();
    }

    private void addTodo() {
        System.out.print("할 일 제목: ");
        String title = scanner.nextLine();
        System.out.print("설명: ");
        String description = scanner.nextLine();
        todoService.addTodo(title, description);
        System.out.println("할 일이 추가되었습니다.");
    }

    private void completeTodo() {
        showTodayTodos();
        System.out.print("완료할 항목 번호: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();
        todoService.markTodoAsCompleted(index);
        System.out.println("할 일이 완료되었습니다.");
    }

    private void removeTodo() {
        showTodayTodos();
        System.out.print("삭제할 항목 번호: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();
        todoService.removeTodo(index);
        System.out.println("할 일이 삭제되었습니다.");
    }

    private void editTodo() {
        showTodayTodos();
        System.out.print("수정할 항목 번호: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        System.out.print("새로운 제목 입력 (수정하지 않으려면 Enter): ");
        String newTitle = scanner.nextLine();

        System.out.print("새로운 설명 입력 (수정하지 않으려면 Enter): ");
        String newDescription = scanner.nextLine();

        todoService.editTodo(index, newTitle, newDescription);
    }

    public static void main(String[] args) {
        new Application().run();
    }
}