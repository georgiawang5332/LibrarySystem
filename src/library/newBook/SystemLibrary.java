package library.newBook;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer; //消費者
/**
 * @since  2018/09/28
 * @author GG
 * 優先印出圖書資訊
 * 
 * 	a.編號 
	b.中文書名 
	c.英文書名(可選) 
	d.ISBN 
	e.價格 
	f.借／還記錄 
	g.借出與否
 * 
 * 
 */
public class SystemLibrary {
	public ArrayList<Book> BookList = new ArrayList<Book>();
	public Scanner input = new Scanner(System.in);
	
	public SystemLibrary() {
        loadData();
    }
	void systemlib() {
		System.out.print("請輸入選項數字(1~5)=>");
		String print = input.nextLine();
		while(true) {
			/******************************************
			 ***查詢
			*******************************************/
			if(print.equals("1")) {
				System.out.print("請輸入查詢資訊:　a.編號 b.中文書名 c.英文書名 d.ISBN");
				input = new Scanner(System.in);
				String searchINFO = input.nextLine();
				
				if(searchINFO.equals("a")) {
					System.out.println("編號:  ");
					input = new Scanner(System.in);
					String NoName = input.nextLine();
					//尚未解決Exception in thread "main" java.util.NoSuchElementException: no line found問題
//					System.out.println(story);
					ArrayList<Book> resultList = findBooksByNoName(NoName);
					System.out.println("-------------------------------------------------查詢結果------------------------------------------------------");
	                if (!resultList.isEmpty()) {
	                	printBookList(resultList);
	                }
				}else if(searchINFO.equals("b")) {
					System.out.println("中文書名: ");
					
				}else if(searchINFO.equals("c")){
					System.out.println("中文書名: ");
				}else if(searchINFO.equals("d")) {
					System.out.println("中文書名: ");
				}else {
					System.out.println("輸入錯誤，請重新輸入。");
				}
			/******************************************
			 ***借閱
			*******************************************/		
			}else if(print.equals("2")) {
				System.out.println("請輸入借閱書名: ");
				String NoName = input.nextLine();
				System.out.println("--------------------------------------------------借閱結果-------------------------------------------------------------");
                checkOutBook(NoName);
            /******************************************
             ***還書
    		*******************************************/
			}else if(print.equals("3")) {
				 System.out.print("請輸入 國際標準書號(ISBN)=>");
	             input = new Scanner(System.in, "big5");
	             String isbn = input.nextLine().toUpperCase().trim();
	             System.out.println("-------------------------------------------------還書結果------------------------------------------------------");
	             returnBook(isbn);
	       /******************************************
	       ***捐書(新增)
	       *******************************************/
			}else if(print.equals("4")) {
				System.out.print("請輸入 國際標準書號(ISBN),中文書名,英文書名,價格,數量(以逗點隔開)=>");
                input = new Scanner(System.in, "big5");
                String[] bookInfo = input.nextLine().toUpperCase().trim().split(",");
                System.out.println("-------------------------------------------------捐書(新增)結果------------------------------------------------------");
                if (bookInfo.length != 5) {
                    System.out.println("新增書籍的資訊不足!請重新操作");
                } else {
                    addBook(bookInfo);
                }
			}
			input.close();
		}
	}
	
	
	
	//查詢
	private ArrayList<Book> findBooksByNoName(String bookNameANo) {
		ArrayList<Book> resultNumber = new ArrayList<Book>();
		for(Book b : BookList) {
			if(((String) b.numberB).startsWith(bookNameANo) || ((String) b.chineseB).startsWith(bookNameANo) || ((String) b.engB).startsWith(bookNameANo)) {
				resultNumber.add(b);
			}
		}
		return resultNumber;
	}
	private void printBookList(ArrayList<Book> BookList) {
		BookList.forEach(new Consumer<Book>(){
			int count = 1;
//			@Override
			public void accept(Book b) {
				System.out.println(count + "." + b.toString());
				count++;
			}
		});
	}
	//借閱
    private void checkOutBook(String name) {
    	
        Book book = queryByName(name);
        if (book != null) {
            if (book.onShelf > 0) {
                book.onShelf = book.onShelf - 1;
                System.out.println("借閱書名:" + book.chineseB + "(" + book.engB + ")" + " 數量:" + 1);
            } else {
                System.out.println(book.chineseB + "(" + book.engB + ") 已全部借出");
            }
        } else {
            System.out.println("書名沒有登記在本館目錄");
        }
    }
    private Book queryByName(String bookName) {
        Book book = BookList.stream()
                .filter(a -> a.chineseB.equals(bookName) || ((String) a.engB).equalsIgnoreCase(bookName))
                .findAny().orElse(null);
        return book;
    }
    //還書
    private void returnBook(String isbn) {
        Book book = queryByISBN(isbn);
        if (book != null) {
            book.onShelf++;
            System.out.println("歸還後架上數量：" + book.onShelf);
        } else {
            System.out.println("國際標準書號(ISBN)=" + isbn + " 沒有登記在本館目錄");
        }
    }
    private Book queryByISBN(String isbn) {
        Book book = BookList.stream()
                .filter(a -> a.isbn.equals(isbn))
                .findAny().orElse(null);
        return book;
    }
    //捐書(新增)
    private void addBook(String... bookInfo) {
    	Book book = new Book();
//        Book book = new Book(bookInfo[0], bookInfo[1], bookInfo[2], Double.parseDouble(bookInfo[3]), Integer.parseInt(bookInfo[4]));
        boolean flag = false;
        for (Book b : BookList) {
            if (b.chineseB.equals(book.chineseB) && ((String) b.engB).equalsIgnoreCase(book.engB)) {
//          if (b.chineseBook.equals(book.chineseBook) && b.engBook.equalsIgnoreCase(book.engBook)) {
                b.onShelf++;
                System.out.println("新增書籍已存在!數量由" + (b.onShelf - 1) + "=>" + b.onShelf);
                System.out.println(b.toString());
                flag = true;
                break;
            }
        }
        if (flag == false) {
            BookList.add(book);
            System.out.println(book.toString());
        }
    }
    
    //倉庫裡面書籍
    private void loadData() {
        Book book1 = new Book("ABCD111111", "爪哇1", "java1", 450, 1);
        Book book2 = new Book("ABCD222222", "爪哇2", "java2", 450, 2);
        Book book3 = new Book("ABCD333333", "爪哇3", "java3", 450, 3);
        Book book4 = new Book("ABCD444444", "爪哇4", "java4", 450, 4);
        Book book5 = new Book("ABCD555555", "爪哇5", "java5", 450, 5);
        Book book6 = new Book("ABCD666666", "爪哇6", "java6", 450, 6);
        Book book7 = new Book("ABCD777777", "爪哇7", "java7", 450, 7);
        BookList.add(book1);
        BookList.add(book2);
        BookList.add(book3);
        BookList.add(book4);
        BookList.add(book5);
        BookList.add(book6);
        BookList.add(book7);
    }
    
    //Start first
	public void PrintMenu() {
		System.out.println("歡迎蒞臨 WC圖書館");
		System.out.println("1. 查詢");
		System.out.println("2. 借閱");
		System.out.println("3. 還書");
		System.out.println("4. 捐書");
		System.out.println("5. 退出系統");
	}
}


