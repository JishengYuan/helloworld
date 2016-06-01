package com.sinobridge.eoss.test.sales;

import java.util.HashMap;
import java.util.List;

import com.sinobridge.base.core.spring.SpringContext;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.contract.dto.SalesContractInfoAndStatus;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;

public class SalesTest {

    public static void seachSalesContractPage() {
        SalesContractService salesContractService = (SalesContractService) SpringContext.getContext().getBean("salesContractService");
        HashMap<String, Object> seachMap = new HashMap<String, Object>();
        seachMap.put(SalesContractModel.CREATOR, Long.parseLong("1"));
        seachMap.put("orderBy", "c.CreatTime");
        seachMap.put("contractStateNotEquals", "CG");
        PaginationSupport p = salesContractService.findPageBySearchMap(seachMap, 1, 10);
        List<SalesContractInfoAndStatus> l = (List<SalesContractInfoAndStatus>) p.getItems();
        System.out.println(l.get(0).getContractCode());
        System.out.println(l.get(0).getContractName());
        System.out.println(l.get(0).getContractState());
    }

    /*    public static void main(String[] args) throws Exception {
            //seachSalesContractPage();
            BufferedReader input;
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("f:\\456.txt")));
            try {
                int sta = 0;
                String s = new String();
                input = new BufferedReader(new FileReader("f:\\123.txt"));
                while ((s = input.readLine()) != null) {
                    // 判断是否读到了最后一行    
                    if (!s.equals("")) {
                        s = s.replaceAll("\\s{2}", "F");
                        //2F,3F,4F
                        String info[] = s.split("[F]+");
                        if (info.length > 0) {
                            int i = s.lastIndexOf("F");
                            if (i == 7) {
                                sta++;
                                String sql = "INSERT INTO table_name (addressCode, provinceName) VALUES ('" + info[0] + "','" + info[1] + "');\n";
                                writer.write(sql);
                                System.out.println("INSERT INTO table_name (addressCode, provinceName) VALUES ('" + info[0] + "','" + info[1] + "')");
                            } else if (i == 8) {
                                if (!info[1].equals("市辖区") && !info[1].equals("县") && !info[1].equals("自治区直辖县级行政区划")) {
                                    sta++;
                                    String sql1 = "INSERT INTO table_name (addressCode, cityName) VALUES ('" + info[0] + "','" + info[1] + "');\n";
                                    writer.write(sql1);
                                    System.out.println("INSERT INTO table_name (addressCode, cityName) VALUES ('" + info[0] + "','" + info[1] + "')");
                                }
                            } else if (i == 9) {
                                if (!info[1].equals("县") && !info[1].equals("市辖区") && !info[1].equals("自治区直辖县级行政区划")) {
                                    sta++;
                                    String sql2 = "INSERT INTO table_name (addressCode, countyName) VALUES ('" + info[0] + "','" + info[1] + "');\n";
                                    writer.write(sql2);
                                    System.out.println("INSERT INTO table_name (addressCode, countyName) VALUES ('" + info[0] + "','" + info[1] + "')");
                                }
                            } else {
                                System.out.println("存在其他的i" + i);
                            }
                        }
                    }
                }
                System.out.println("共" + sta + "条");
                input.close();
                writer.close();
            } catch (FileNotFoundException e) { // TODO Auto-generated catch block  
                e.printStackTrace();
            }
        }*/
    public static void main(String[] args) throws Exception {
        String businessOrderModelIds = "";
        for (int j = 0; j <= 5; j++) {
            businessOrderModelIds += j + ",";
        }
        businessOrderModelIds = businessOrderModelIds.substring(0, businessOrderModelIds.lastIndexOf(","));
        System.out.println(businessOrderModelIds);
    }

}
