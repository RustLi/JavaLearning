package java_common.annotation;

@FetchHandle(tableName="test")
@FetchList(tableList = {"test1","test2"})
public class Main {
    public static void main(String[] args) {
//        single();
        fetchList();
    }

    private static void single(){
        Main aMain = new Main();
        Class<?> aCls = aMain.getClass();
        FetchHandle fetchHandle = aCls.getAnnotation(FetchHandle.class);
        System.out.println("fetchHandle = " + fetchHandle);

        if (fetchHandle != null){
            String name = fetchHandle.tableName();
            System.out.println("name = " + name);
        }
    }

    private static void fetchList(){
        Main aMain = new Main();
        Class<?> aCls = aMain.getClass();
        FetchList fetchList = aCls.getAnnotation(FetchList.class);
        System.out.println("fetchList = " + fetchList);

        if (fetchList == null){
            System.out.println("fetchList is null");
        }
        
        if (fetchList != null){
            for (String tableName : fetchList.tableList()) {
                System.out.println("tableName = " + tableName);
            }
        }
    }
}
