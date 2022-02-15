package com.zsq.study.test;

public class SQLBuild {

    public static void main(String[] args) {



        for(int month=1;month<=12;month++) {
            if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
                for(int day=1;day<=31;day++){
                    sqlBuild(month,day);
                }
            }else if(month==2){
                for(int day=1;day<=28;day++){
                    sqlBuild(month,day);
                }
            }else {
                for(int day=1;day<=30;day++){
                    sqlBuild(month,day);
                }
            }
        }
    }

    private static void sqlBuild(int month,int day){
        String realMonth="";
        if(month<10){
            realMonth= 0+String.valueOf(month);
        }else {
            realMonth= String.valueOf(month);
        }
        String realday="";
        if(day<10){
            realday= 0+String.valueOf(day);
        }else{
            realday= String.valueOf(day);
        }


        String  tableName="tbFundRecord2021"+realMonth+realday;

        String sql = "DROP TABLE IF EXISTS `"+tableName+"`;\n" +
                "CREATE TABLE `"+tableName+"`  (\n" +
                "  `lId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',\n" +
                "  `strLevel1Code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '一级类型 ',\n" +
                "  `strLevel2Code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '二级类型',\n" +
                "  `strLevel3Code` varchar(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '三级类型',\n" +
                "  `strLevel4Code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '四级类型',\n" +
                "  `strLevel5Code` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '五级类型',\n" +
                "  `strLevel6Code` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '六级类型',\n" +
                "  `strLevel7Code` varchar(21) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '七级类型',\n" +
                "  `strLevel8Code` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '八级类型',\n" +
                "  `strLevel9Code` varchar(27) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '九级类型',\n" +
                "  `strLevel10Code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '十级类型',\n" +
                "  `nMasterData` int(2) NOT NULL DEFAULT 0 COMMENT '是否为主数据0：主数据；1：拆分数据',\n" +
                "  `nPayMethod` int(2) NOT NULL COMMENT '支付方式 1:线上；2:线上',\n" +
                "  `strCountBusinessTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '业务时间字段',\n" +
                "  `nDataSource` int(2) NOT NULL COMMENT '数据来源 1:云贷款；2:卓越；3:达达；4:1.0',\n" +
                "  `nCompanyCode` int(2) NOT NULL COMMENT '公司编码 1:云贷；2:上饶；3:阔礼；4:亿鑫达；5:达纷期',\n" +
                "  `nfundSource` int(11) NOT NULL COMMENT '业务来源 1:业务 2:达飞 3:银行卡 4:公共虚拟户 5:个人虚拟户',\n" +
                "  `nCheckData` int(2) NOT NULL DEFAULT 0 COMMENT '是否已验证 0:未验证；1:已验证',\n" +
                "  `lIn` bigint(11) NOT NULL DEFAULT 0 COMMENT '流入',\n" +
                "  `lOut` bigint(11) NOT NULL DEFAULT 0 COMMENT '流出',\n" +
                "  `lPrincipal` bigint(11) NOT NULL DEFAULT 0 COMMENT '本金',\n" +
                "  `lInterest` bigint(11) NOT NULL DEFAULT 0 COMMENT '利息',\n" +
                "  `lOverdueAmount` bigint(11) NOT NULL DEFAULT 0 COMMENT '违约金',\n" +
                "  `strBusinessSN` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '业务序号',\n" +
                "  `strDealSN` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资金序号',\n" +
                "  `strThirdSN` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '三方序号',\n" +
                "  `nFundId` int(10) NOT NULL DEFAULT 0 COMMENT '资金方id',\n" +
                "  `lThirdId` bigint(11) NOT NULL DEFAULT 0 COMMENT '三方id',\n" +
                "  `strThirdName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '三方名称',\n" +
                "  `strChannelMerchantNum` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '渠道编号',\n" +
                "  `strChannelMerchantName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '渠道名称',\n" +
                "  `strComment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',\n" +
                "  `dtBusinessTime` datetime NOT NULL COMMENT '业务时间',\n" +
                "  `dtCreateTime` datetime NOT NULL COMMENT '创建时间',\n" +
                "  `dtUpdateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',\n" +
                "  PRIMARY KEY (`lId`) USING BTREE,\n" +
                "  INDEX `strLevel1Code_index`(`strLevel1Code`) USING BTREE,\n" +
                "  INDEX `strLevel2Code_index`(`strLevel2Code`) USING BTREE,\n" +
                "  INDEX `nfundSource`(`nfundSource`, `strBusinessSN`, `strDealSN`, `strThirdSN`) USING BTREE\n" +
                ") ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资金对帐明细表' ROW_FORMAT = DYNAMIC;\n" +
                "\n" +
                "SET FOREIGN_KEY_CHECKS = 1;";
        System.out.println(sql);


        String fileName = "/Users/zhoushengqiang/Documents/code/study/src/main/java/com/zsq/study/test/createTableTbFundRecordX.sql.txt";
        //按方法A追加文件
        AppendToFile.appendMethodA(fileName, sql);
    }


}
