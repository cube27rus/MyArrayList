package core;

/**
 * Created by Cube27 on 19.07.2017.
 */
public class Test {



    public static void main(String[] args) {
        MyList<String> st = new MyList<>();
        st.add("ololo");
        st.add("Trololo");
        st.add("333Trololo");
        st.add("444Trololo");
        System.out.println(st.toString());


        st.remove(3);
        System.out.println(st.toString());
    }

}
