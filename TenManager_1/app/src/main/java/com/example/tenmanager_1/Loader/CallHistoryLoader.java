package com.example.tenmanager_1.Loader;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import com.example.tenmanager_1.Data.CallHistoryData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 전지훈 on 2017-11-06.
 */

public class CallHistoryLoader {
    private List<CallHistoryData> datas = new ArrayList<>();
    private Context context;

    public CallHistoryLoader(Context context){
        this.context = context;
    }

    public List<CallHistoryData> getContacts() {
        ContentResolver resolver = context.getContentResolver();

        // 1. 데이터 컨텐츠 URI (자원의 주소)를 정의
        Uri callUri = CallLog.Calls.CONTENT_URI;

        // 2. 데이터에서 가져올 컬럼명을 정의
        String projections[] = {CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.TYPE};

/*        String selection = CallLog.Calls.TYPE + "=?"; // Where절 타입이
        String selectionArgs[] = {CallLog.Calls.INCOMING_TYPE, CallLog.Calls.OUTGOING_TYPE};*/

        String sortOrder=CallLog.Calls.DATE + " DESC";

        // 3. Content Resolver로 쿼리를 날려서 데이터를 가져온다.
        Cursor cursor = resolver.query(callUri,    // 데이터의 주소 (URI)
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

                int dateIndex = cursor.getColumnIndex(projections[2]);

                long setupTime = 0;
                try {
                    setupTime = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0).firstInstallTime;
                    //setupTime = setupTime - 1000*60*60*24;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                long callTime = cursor.getLong(dateIndex);
                if(callTime < setupTime){
                    continue;
                }

                Date date = new Date(callTime);
                String date2 = date.toString();

                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
                String date3 = sdf.format(new Date(date2));


                int typeIndex = cursor.getColumnIndex(projections[3]);
                int checkType = cursor.getInt(typeIndex);
                String type = null;

                // 5. 내가 설계한 Data 클래스에 담아준다.
                CallHistoryData data = new CallHistoryData();
                data.setName(name);
                data.setTel(tel);
                data.setDate(date3);

                switch (checkType){
                    case CallLog.Calls.INCOMING_TYPE:
                        type = "수신";
                        break;
                    case 2:
                        type = "발신";
                        break;
                    case 3:
                        type = "부재중";
                        break;
                    case 4:
                        type = "거절";
                        break;
                }

                data.setType(type);

                // 6. 여러개의 객체를 담을 수 있는 저장소에 적재한다.
                datas.add(data);
            }
        }
        // * 중요 : 사용 후 close 를 호출하지 않으면 메모리 누수가 발생할 수 있다.
        cursor.close();

        return datas;
    }

}
