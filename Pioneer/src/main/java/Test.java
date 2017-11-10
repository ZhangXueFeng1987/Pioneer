
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test="0|Test";
		System.out.println((test==null?"":test).substring((test==null?"":test).indexOf("|")+1));
	}

}
