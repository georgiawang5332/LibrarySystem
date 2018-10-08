# LibrarySystem
test LibrarySystem for java
/**
*SystemLib.java
*
*/


package library.review;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * @author GG
 * @時間: 20181003
 * 
 *      1.實作顯示選單功能 1.1請求使用者輸入功能代號 2.選取選項"選項1.查詢 2.借閱 3.還書 4.捐書 5.退出系統" 3.查詢: 選項:
 *      a.編號 b.中文書名 c.英文書名 d.ISBN 4.借閱: 如果有此書于本館中，顯示借書名: 書 (book) ; 數量: 1 ;;
 *      否則已全數借出 5.還書: 6.捐書: 7.退出系統: 8.
 */
public class SystemLib {
    private static final String book = null;
	public ArrayList<Book> BookList = new ArrayList<Book>();
	public Scanner input = new Scanner(System.in);
	private Object books;
	private String isbn;
	private String name;

	public SystemLib() {
		loadDate();
	}

	public void systemLibrary() {
		System.out.println("填入選項(1~5): =>");
		String print = input.nextLine();

		while (true) {
			if (print.equals("1") && print.equals("2") && print.equals("3") && print.equals("4") && print.equals("5")) {
				// 查詢 "1".equals(print)
				if ("1".equals(print)) {
					System.out.println("查詢選項: a.編號 b.中文書名 c.英文書名 d.ISBN");
					input = new Scanner(System.in);
					String search = input.nextLine();

					// a.編號
					if ("a".equals(search)) {
						System.out.println("查詢編號: ");
						ArrayList<Book> result = findBooks(search);

						// ----------------------------------------
						if (!result.isEmpty()) {
							printBookList(result);
						}
						// b.中文書名
					} else if ("b".equals(search)) {
						System.out.println("查詢中文書名: ");
						ArrayList<Book> result = findBooks(search);
						if (!result.isEmpty()) {
							printBookList(result);
						}
						// c.英文書名
					} else if ("c".equals(search)) {
						System.out.println("查詢英文書名: ");
						ArrayList<Book> result = findBooks(search);
						if (!result.isEmpty()) {
							printBookList(result);
						}
						// d.ISBN
					} else if ("a".equals(search)) {
						System.out.println("查詢ISBN: ");
						ArrayList<Book> result = findBooks(search);
						if (!result.isEmpty()) {
							printBookList(result);
						}
					} else {
						System.out.println("輸入錯誤!!!!!!");
					}
					input.close();
					// 借閱
				} else if ("2".equals(print)) {
					System.out.println("借閱: ");
					input = new Scanner(System.in);
					String search = input.nextLine();
					checkoutBook(search);
					input.close();
					// 還書
				} else if ("3".equals(print)) {
					System.out.println("ISBN: ");
					input = new Scanner(System.in);
					String search = input.nextLine();
					returnBook(search);
					input.close();
					// 捐書
				} else if ("4".equals(print)) {
					System.out.println("捐書: ");
					input = new Scanner(System.in);
					String[] bookinfo = input.nextLine().toUpperCase().trim().split(",");

					if (bookinfo.length != 5) {
						System.out.println("新增資訊不足!!");
					} else {
						addBook(bookinfo);
					}
					input.close();
				}
			} else {
				System.out.println("您輸入有誤，請再次填入選項(1~5): =>");
				input = new Scanner(System.in);
				String search = input.nextLine();
				System.out.println(search);
			}
			input.close();
		}// while
	}

	// 捐書 - addBook
	private void addBook(String[] bookinfo) {
		// TODO 自動產生的方法 Stub
		Book book = new Book(bookinfo[0], bookinfo[1], bookinfo[2], Double.parseDouble(bookinfo[3]),
				Integer.parseInt(bookinfo[4]));
		boolean flag = false;
		for (Book b : BookList) {
			if (b.chineseB.equals(book.chineseB) && (book.engB).equalsIgnoreCase(book.engB)) {// equalsIgnoreCase为了执行忽略大小写的比较
				b.onShelf++;
				System.out.println("新增書籍已存在!數量由" + (b.onShelf - 1) + "=>" + b.onShelf);
				System.out.println(b.toString());// toString(): 這將返回表示該整數的值的String對象。
				flag = true;
				break;
			}
			if (flag == false) {
				BookList.add(book);
				System.out.println(book.toString());// ???
			}
		}
	}

	// 借閱 - checkoutBook
	private void checkoutBook(String name) {
		// TODO 自動產生的方法 Stub
		if (book != null) {
			Book book = queryByName(name);
			if (book.onShelf > 0) {
				book.onShelf = book.onShelf - 1;
				System.out.println("借書名: " + book.chineseB + "(" + book.engB + ")" + ";" + "數量: " + 1);
			} else {
				System.out.println(book.chineseB + "( + book.engB + " + "已全數借出");
			}
		}
	}

	// 查詢 - findBooks
	private ArrayList<Book> findBooks(String bookName) {
		ArrayList<Book> resultFind = new ArrayList<Book>();
		for (Book b : BookList) {
			if (b.numB.startsWith(bookName) || b.chineseB.startsWith(bookName) || b.engB.startsWith(bookName)
					|| b.isbnB.startsWith(bookName)) {
				resultFind.add(b);
			} else {
				System.out.println("書名無登記於本館中!!!");
			}
		}
		return resultFind;
	}

	// 查詢 - printBookList
	private void printBookList(ArrayList<Book> booklist) {
		booklist.forEach(new Consumer<Book>() {
			int count = 1;

			@Override
			public void accept(Book t) {// Consumer
				// TODO 自動產生的方法 Stub
				System.out.println(count + "." + t.toString());
				count++;
			}
		});
	}

	// 借閱
	private Book queryByName(String bookName) {
		Book book = BookList.stream().filter(a -> a.chineseB.equals(bookName) || ((String) a.engB).equals(bookName))
				.findAny().orElse(null);
		return book;
	}

	// 還書
	private Book returnBook(String isbn) {
		Book book = queryByIsbn(isbn);
		if (book != null) {
			book.onShelf++;
			System.out.println("歸還後還在架上數量");
		}
		return book;
	}

	// queryByIsbn
	private Book queryByIsbn(String isbn) {
		// TODO 自動產生的方法 Stub
		Book book = BookList.stream().filter(bl -> bl.equals(isbn)).findAny().orElse(null);
		return book;
	}

	// 建立書本，總共6本。
	private void loadDate() {
		Book book1 = new Book("A111111", "爪爪01", "java01", "100", 1);
		Book book2 = new Book("A111112", "爪爪02", "java02", "200", 2);
		Book book3 = new Book("A111113", "爪爪03", "java03", "300", 3);
		Book book4 = new Book("A111114", "爪爪04", "java04", "400", 4);
		Book book5 = new Book("A111115", "爪爪05", "java05", "500", 5);
		Book book6 = new Book("A111116", "爪爪06", "java06", "600", 6);
		BookList.add(book1);
		BookList.add(book2);
		BookList.add(book3);
		BookList.add(book4);
		BookList.add(book5);
		BookList.add(book6);
	}

	// 印出圖書系統選項
	public void printMenu() {
		System.out.println("welcome to my Library !!");
		System.out.println("1. 查詢");
		System.out.println("2. 借閱");
		System.out.println("3. 還書");
		System.out.println("4. 捐書");
		System.out.println("5. 退出系統");
	}

}


/**
*Book.java
*
*/


package library.review;

/**
 * @author GG
 * @時間: 20181003
 */
public class Book {
    
	//建構子原因不清楚,建立書本，總共6本。
	public Book(String string, String string2, String string3, String string4, int i) {
		// TODO 自動產生的建構子 Stub
	}
	//捐書 - addBook
	public Book(String string, String string2, String string3, double parseDouble, int parseInt) {
		// TODO 自動產生的建構子 Stub
		
	}
	public String numB;
	public String chineseB;
	public String engB;
	public String isbnB;
	
	public int onShelf;
	public String isbn;
}



/**
*Main.java
*
*/

package library.review;
/**
 * @author GG
 * @時間: 20181003
 */
public class Main {

    public static void main(String[] args) {
		// TODO 自動產生的方法 Stub
		SystemLib sysBook = new SystemLib();
		sysBook.printMenu();
		sysBook.systemLibrary();
	}
}
