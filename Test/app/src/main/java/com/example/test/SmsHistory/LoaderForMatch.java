package com.example.test.SmsHistory;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.example.test.Data.ContactData;
import com.example.test.Loader.SearchData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LoaderForMatch {

    //private List<ContactData> datas = new ArrayList<>();
    private Context context;
    private HashMap<String, String> map = new HashMap<>();

    public LoaderForMatch(Context context){
        this.context = context;
    }

    public HashMap<String, String> getContacts() {
        ContentResolver resolver = context.getContentResolver();

        // 1. 데이터 컨텐츠 URI (자원의 주소)를 정의
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 테이블명과 같이 생각하면 된다.

        // 2. 데이터에서 가져올 컬럼명을 정의
        String projections[] = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME               // 화면에 표시되는 이름
                , ContactsContract.CommonDataKinds.Phone.NUMBER};                   // 실제 전화번호

        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;

        // 3. Content Resolver로 쿼리를 날려서 데이터를 가져온다.
        Cursor cursor = resolver.query(phoneUri,    // 데이터의 주소 (URI)
                projections,    // 가져올 데이터 컬럼명 배열 (projection)
                null,           // 조건절에 들어가는 컬럼명들 지정
                null,           // 지정된 컬럼명과 매핑되는 실제 조건 값
                sortOrder);          // 정렬


        // 4. 반복문을 통해 cursor에 담겨있는 데이터를 하나씩 추출한다.
        if (cursor != null) {   // cursor에 데이터 존재여부 체크
            while (cursor.moveToNext()) {
                // moveToNext() 다음 데이터로 이동, 데이터가 있으면 계속 while문이 동작
                // moveToNext()가 true, false를 반환하기 때문에 while문으로 하는 것이 적합
                // 4.1 위에 정의한 프로젝션의 컬럼명으로 cursor 있는 인덱스값을 조회하고

                int nameIndex = cursor.getColumnIndex(projections[0]);
                String name = cursor.getString(nameIndex);

                int telIndex = cursor.getColumnIndex(projections[1]);
                // 4.2 해당 index를 사용해서 실제값을 가져온다.
                String tel = cursor.getString(telIndex);
                String ph = splitPhoneNumber(tel);
                // String tel = cursor.getString(2);  라고 해도 되는데,
                // index값을 불러와 이름으로 해주면 실수하지 않고, 불러올 수 있다.

/*                // 5. 내가 설계한 Data 클래스에 담아준다.
                ContactData data = new ContactData();
                data.setName(name);
                data.setTel(tel);
                // 6. 여러개의 객체를 담을 수 있는 저장소에 적재한다.
                datas.add(data);*/

                map.put(ph, name); // 번호 -> 키
            }
        }
        // * 중요 : 사용 후 close 를 호출하지 않으면 메모리 누수가 발생할 수 있다.
        cursor.close();

        return map;
    }

    public String splitPhoneNumber(String phoneNumberBeforeSplit) {
        String phoneNumberAfterSplit = "";
        String split[] = phoneNumberBeforeSplit.split("-");
        for (int i = 0; i < split.length; i++) {
            phoneNumberAfterSplit += split[i];
        }
        return phoneNumberAfterSplit;
    }

}
