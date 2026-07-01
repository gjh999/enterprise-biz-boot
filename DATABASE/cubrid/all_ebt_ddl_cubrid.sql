-- 개정이력: 2026.06.17  구재호   TB_ 명명규칙 현행화 및 기업 전용 테이블 추가 (Spring Boot + Thymeleaf 전환)
CREATE TABLE IDS  (
  TABLE_NAME varchar(60) NOT NULL,  -- 대상 테이블명
  NEXT_ID numeric(30,0)DEFAULT 0 NOT NULL,  -- 다음 ID 값
  CONSTRAINT IDS_PK PRIMARY KEY (TABLE_NAME)
);

CREATE TABLE COMTECOPSEQ (
  TABLE_NAME varchar(20) DEFAULT '' NOT NULL,  -- 대상 테이블명
  NEXT_ID numeric(30,0) DEFAULT 0 NOT NULL,  -- 다음 ID 값
  CONSTRAINT COMTECOPSEQ_PK PRIMARY KEY (TABLE_NAME)
) ;

CREATE TABLE TB_CMMN_CL_CODE (
  CL_CODE char(3) NOT NULL,  -- 분류코드
  CL_CODE_NM varchar(180),  -- 분류코드명
  CL_CODE_DC varchar(600),  -- 분류코드 설명
  USE_AT char(1),  -- 사용여부(Y/N)
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  CONSTRAINT TB_CMMN_CL_CODE_PK PRIMARY KEY (CL_CODE)
) ;

CREATE TABLE TB_CMMN_CODE (
  CODE_ID varchar(18) NOT NULL,  -- 코드ID
  CODE_ID_NM varchar(180),  -- 코드ID명
  CODE_ID_DC varchar(600),  -- 코드ID 설명
  USE_AT char(1),  -- 사용여부(Y/N)
  CL_CODE char(3),  -- 분류코드
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  CONSTRAINT TB_CMMN_CODE_PK PRIMARY KEY (CODE_ID)
) ;

CREATE TABLE TB_CMMN_DETAIL_CODE (
  CODE_ID varchar(18) NOT NULL,  -- 코드ID
  CODE varchar(45) NOT NULL,  -- 상세코드
  CODE_NM varchar(180),  -- 상세코드명
  CODE_DC varchar(600),  -- 상세코드 설명
  USE_AT char(1),  -- 사용여부(Y/N)
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  CONSTRAINT TB_CMMN_DETAIL_CODE_PK PRIMARY KEY (CODE_ID,CODE)
) ;

CREATE TABLE TB_ORGNZT_INFO (
  ORGNZT_ID char(20) DEFAULT '' NOT NULL,  -- 조직ID
  ORGNZT_NM varchar(60) NOT NULL,  -- 조직명
  ORGNZT_DC varchar(300),  -- 조직 설명
  CONSTRAINT TB_ORGNZT_INFO_PK PRIMARY KEY (ORGNZT_ID)
) ;

CREATE TABLE TB_AUTHOR_GROUP_INFO (
  GROUP_ID char(20) DEFAULT '' NOT NULL,  -- 그룹ID
  GROUP_NM varchar(180) NOT NULL,  -- 그룹명
  GROUP_CREAT_DE char(60) NOT NULL,  -- 생성일자
  GROUP_DC varchar(300),  -- 그룹 설명
  CONSTRAINT TB_AUTHOR_GROUP_INFO_PK PRIMARY KEY (GROUP_ID)
) ;

CREATE TABLE TB_AUTHOR_INFO (
  AUTHOR_CODE varchar(90) DEFAULT '' NOT NULL,  -- 권한코드
  AUTHOR_NM varchar(180) NOT NULL,  -- 권한명
  AUTHOR_DC varchar(600),  -- 권한 설명
  AUTHOR_CREAT_DE char(60) NOT NULL,  -- 생성일자
  CONSTRAINT TB_AUTHOR_INFO_PK PRIMARY KEY (AUTHOR_CODE)
) ;

CREATE TABLE TB_ROLES_HIERARCHY (
  PARNTS_ROLE varchar(90) NOT NULL,  -- 부모롤
  CHLDRN_ROLE varchar(90) NOT NULL,  -- 자식롤
  CONSTRAINT TB_ROLES_HIERARCHY_PK PRIMARY KEY (PARNTS_ROLE,CHLDRN_ROLE)
) ;

CREATE TABLE TB_ROLE_INFO (
  ROLE_CODE varchar(150) DEFAULT '' NOT NULL,  -- 역할코드
  ROLE_NM varchar(180) NOT NULL,  -- 역할명
  ROLE_PTTRN varchar(900),  -- 롤PTTRN
  ROLE_DC varchar(600),  -- 역할설명
  ROLE_TY varchar(240),  -- 역할유형
  ROLE_SORT varchar(30),  -- 역할정렬
  ROLE_CREAT_DE char(60) NOT NULL,  -- 롤생성일자
  CONSTRAINT TB_ROLE_INFO_PK PRIMARY KEY (ROLE_CODE)
) ;

CREATE TABLE TB_AUTHOR_ROLE_RELATE (
  AUTHOR_CODE varchar(90) NOT NULL,  -- 권한코드
  ROLE_CODE varchar(150) NOT NULL,  -- 역할코드
  CREAT_DT DATETIME,  -- 생성시각
  CONSTRAINT TB_AUTHOR_ROLE_RELATE_PK PRIMARY KEY (AUTHOR_CODE,ROLE_CODE)
) ;

CREATE TABLE TB_GNRL_MBER (
  MBER_ID varchar(60) DEFAULT '' NOT NULL,  -- 회원ID
  PASSWORD varchar(600) NOT NULL,  -- 비밀번호(해시)
  PASSWORD_HINT varchar(300) NOT NULL,  -- 비밀번호 힌트
  PASSWORD_CNSR varchar(300) NOT NULL,  -- 비밀번호 정답
  IHIDNUM varchar(39),  -- 주민등록번호
  MBER_NM varchar(150) NOT NULL,  -- 회원명
  ZIP varchar(18),  -- 우편번호
  ADRES varchar(300),  -- 주소
  AREA_NO varchar(12),  -- 지역번호
  MBER_STTUS varchar(45),  -- 회원상태코드
  DETAIL_ADRES varchar(300),  -- 상세주소
  END_TELNO varchar(12),  -- 전화 끝번호
  MBTLNUM varchar(60),  -- 휴대전화
  GROUP_ID char(20),  -- 그룹ID
  MBER_FXNUM varchar(60),  -- 팩스번호
  MBER_EMAIL_ADRES varchar(150),  -- 이메일주소
  MIDDLE_TELNO varchar(12),  -- 전화 중간번호
  SBSCRB_DE DATETIME,  -- 가입일시
  SEXDSTN_CODE char(1),  -- 성별코드
  ESNTL_ID char(20) NOT NULL,  -- 고유ID
  CONSTRAINT TB_GNRL_MBER_PK PRIMARY KEY (MBER_ID)
) ;

CREATE TABLE TB_EMPLYR_SCRTY_ESTBS (
  SCRTY_DTRMN_TRGET_ID varchar(60) NOT NULL,  -- 보안결정대상ID(보통 사용자ID)
  MBER_TY_CODE varchar(45),  -- 회원유형코드
  AUTHOR_CODE varchar(90) NOT NULL,  -- 권한코드
  CONSTRAINT TB_EMPLYR_SCRTY_ESTBS_PK PRIMARY KEY (SCRTY_DTRMN_TRGET_ID)
) ;

CREATE TABLE TB_TMPLAT_INFO (
  TMPLAT_ID char(20) DEFAULT '' NOT NULL,  -- 템플릿ID
  TMPLAT_NM varchar(765),  -- 템플릿명
  TMPLAT_COURS varchar(6000),  -- 템플릿COURS
  USE_AT char(1),  -- 사용여부(Y/N)
  TMPLAT_SE_CODE char(6),  -- 템플릿구분상세코드
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  CONSTRAINT TB_TMPLAT_INFO_PK PRIMARY KEY (TMPLAT_ID)
) ;

CREATE TABLE TB_BBS_MASTER (
  BBS_ID char(20) NOT NULL,  -- 게시판ID
  BBS_NM varchar(765) NOT NULL,  -- 게시판명
  BBS_INTRCN varchar(7200),  -- 게시판 소개
  BBS_TY_CODE char(6) NOT NULL,  -- 게시판 유형코드
  BBS_ATTRB_CODE char(6) NOT NULL,  -- 게시판 속성코드
  REPLY_POSBL_AT char(1),  -- 답글 가능(Y/N)
  FILE_ATCH_POSBL_AT char(1) NOT NULL,  -- 파일첨부 가능(Y/N)
  ATCH_POSBL_FILE_NUMBER numeric(2,0) NOT NULL,  -- 첨부가능 파일 수
  ATCH_POSBL_FILE_SIZE numeric(8,0),  -- 첨부가능 총 용량(단위: 시스템 정의)
  USE_AT char(1) NOT NULL,  -- 사용여부(Y/N)
  TMPLAT_ID char(20),  -- 템플릿ID
  FRST_REGISTER_ID varchar(60) NOT NULL,  -- 최초등록자ID
  FRST_REGIST_PNTTM DATETIME NOT NULL,  -- 최초등록시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  CONSTRAINT TB_BBS_MASTER_PK PRIMARY KEY (BBS_ID)
) ;

CREATE TABLE TB_BBS_USE (
  BBS_ID char(20) NOT NULL,  -- 게시판ID
  TRGET_ID char(20) DEFAULT '' NOT NULL,  -- 사용대상ID
  USE_AT char(1) NOT NULL,  -- 사용여부(Y/N)
  REGIST_SE_CODE char(6),  -- 등록구분코드
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60) NOT NULL,  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  CONSTRAINT TB_BBS_USE_PK PRIMARY KEY (BBS_ID,TRGET_ID)
) ;

CREATE TABLE TB_BBS (
  NTT_ID numeric(20,0) NOT NULL,  -- 게시물ID
  BBS_ID char(20) NOT NULL,  -- 게시판ID
  NTT_NO numeric(20,0),  -- 게시물 번호(정렬/표시용)
  NTT_SJ varchar(6000),  -- 제목
  NTT_CN STRING,
  ANSWER_AT char(1),  -- 답변글 여부(Y/N)
  PARNTSCTT_NO numeric(10,0),  -- 부모글 번호
  ANSWER_LC numeric(8),  -- 답변 계층(레벨)
  SORT_ORDR numeric(8,0),  -- 정렬순서
  RDCNT numeric(10,0),  -- 조회수
  USE_AT char(1) NOT NULL,  -- 사용여부(Y/N)
  NTCE_BGNDE char(20),  -- 공지 시작일시
  NTCE_ENDDE char(20),  -- 공지 종료일시
  NTCR_ID varchar(60),  -- 게시자ID
  NTCR_NM varchar(60),  -- 게시자명
  PASSWORD varchar(600),  -- 비밀번호(해시)
  ATCH_FILE_ID char(20),  -- 첨부파일 묶음ID
  FRST_REGIST_PNTTM DATETIME NOT NULL,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60) NOT NULL,  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  CONSTRAINT TB_BBS_PK PRIMARY KEY (NTT_ID,BBS_ID)
) ;

CREATE TABLE TB_BBS_MASTER_OPTN (
  BBS_ID char(20) DEFAULT '' NOT NULL,  -- 게시판ID
  ANSWER_AT char(1) DEFAULT '' NOT NULL,  -- 답변글 여부(Y/N)
  STSFDG_AT char(1) DEFAULT '' NOT NULL,  -- 만족도 조사 사용(Y/N)
  FRST_REGIST_PNTTM DATETIME DEFAULT SYSDATE NOT NULL,  -- 최초등록시각
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  FRST_REGISTER_ID varchar(60) DEFAULT '' NOT NULL,  -- 최초등록자ID
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  CONSTRAINT TB_BBS_MASTER_OPTN_PK PRIMARY KEY (BBS_ID)
) ;

CREATE TABLE TB_EMPLYR_INFO (
  EMPLYR_ID varchar(60) NOT NULL,  -- 사용자ID
  ORGNZT_ID char(20),  -- 조직ID
  USER_NM varchar(180) NOT NULL,  -- 사용자명
  PASSWORD varchar(600) NOT NULL,  -- 비밀번호(해시)
  EMPL_NO varchar(60),  -- 사번
  IHIDNUM varchar(39),  -- 주민등록번호
  SEXDSTN_CODE char(1),  -- 성별코드
  BRTHDY char(20),  -- 생년월일
  FXNUM varchar(60),  -- 팩스
  HOUSE_ADRES varchar(300),  -- 자택주소
  PASSWORD_HINT varchar(300) NOT NULL,  -- 비밀번호 힌트
  PASSWORD_CNSR varchar(300) NOT NULL,  -- 비밀번호 정답
  HOUSE_END_TELNO varchar(12),  -- 자택전화 끝번호
  AREA_NO varchar(12),  -- 지역번호
  DETAIL_ADRES varchar(300),  -- 상세주소
  ZIP varchar(18),  -- 우편번호
  OFFM_TELNO varchar(60),  -- 사무실전화
  MBTLNUM varchar(60),  -- 휴대전화
  EMAIL_ADRES varchar(150),  -- 이메일
  OFCPS_NM varchar(180),  -- 직책명
  HOUSE_MIDDLE_TELNO varchar(12),  -- 자택전화 중간번호
  GROUP_ID char(20),  -- 그룹ID
  PSTINST_CODE char(8),  -- 소속기관코드
  EMPLYR_STTUS_CODE varchar(45) NOT NULL,  -- 사용자상태코드
  ESNTL_ID char(20) NOT NULL,  -- 고유ID
  CRTFC_DN_VALUE varchar(60),  -- 인증DN
  SBSCRB_DE DATETIME,  -- 가입일시
  CONSTRAINT TB_EMPLYR_INFO_PK PRIMARY KEY (EMPLYR_ID)
) ;

CREATE TABLE TB_EMPLYR_INFO_CHNG_DTLS (
  EMPLYR_ID varchar(60) NOT NULL,  -- 사용자ID
  CHANGE_DE char(20) NOT NULL,  -- 변경일자
  ORGNZT_ID char(20),  -- 조직ID
  GROUP_ID char(20),  -- 그룹ID
  EMPL_NO varchar(60) NOT NULL,  -- 사번
  SEXDSTN_CODE char(1),  -- 성별코드
  BRTHDY char(20),  -- 생년월일
  FXNUM varchar(60),  -- 팩스
  HOUSE_ADRES varchar(300) NOT NULL,  -- 자택주소
  HOUSE_END_TELNO varchar(12),  -- 자택전화 끝번호
  AREA_NO varchar(12),  -- 지역번호
  DETAIL_ADRES varchar(300) NOT NULL,  -- 상세주소
  ZIP varchar(18) NOT NULL,  -- 우편번호
  OFFM_TELNO varchar(60),  -- 사무실전화
  MBTLNUM varchar(60) NOT NULL,  -- 휴대전화
  EMAIL_ADRES varchar(150),  -- 이메일
  HOUSE_MIDDLE_TELNO varchar(12),  -- 자택전화 중간번호
  PSTINST_CODE char(8),  -- 소속기관코드
  EMPLYR_STTUS_CODE varchar(45) NOT NULL,  -- 사용자상태코드
  ESNTL_ID char(20),  -- 고유ID
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  CONSTRAINT TB_EMPLYR_INFO_CHNG_DTLS_PK PRIMARY KEY (EMPLYR_ID,CHANGE_DE)
) ;

CREATE TABLE TB_ZIP (
  ZIP varchar(18) NOT NULL,  -- 우편번호
  SN numeric(10,0) DEFAULT '0' NOT NULL,  -- 일련번호
  CTPRVN_NM varchar(60),  -- 시도명
  SIGNGU_NM varchar(60),  -- 시군구명
  EMD_NM varchar(180),  -- 읍면동명
  LI_BULD_NM varchar(180),  -- 리건물명
  LNBR_DONG_HO varchar(60),  -- 지번동호
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  CONSTRAINT TB_ZIP_PK PRIMARY KEY (ZIP,SN)
) ;

CREATE TABLE TB_BANNER (
  BANNER_ID char(20) DEFAULT '' NOT NULL,  -- 배너ID
  BANNER_NM varchar(180) NOT NULL,  -- 배너명
  LINK_URL varchar(765) NOT NULL,  -- 연계URL
  BANNER_IMAGE varchar(180) NOT NULL,  -- 배너이미지
  BANNER_DC varchar(600),  -- 배너설명
  REFLCT_AT char(1) NOT NULL,  -- REFLCT여부
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  BANNER_IMAGE_FILE varchar(180),  -- 배너이미지파일
  SORT_ORDR numeric(8,0),  -- 정렬순서
  CONSTRAINT TB_BANNER_PK PRIMARY KEY (BANNER_ID)
) ;

CREATE TABLE TB_ENTRPRS_MBER (
  ENTRPRS_MBER_ID varchar(60) DEFAULT '' NOT NULL,  -- 기업회원ID
  ENTRPRS_SE_CODE char(15),  -- 기업구분코드
  BIZRNO varchar(30),  -- 사업자등록번호
  JURIRNO varchar(39),  -- 법인등록번호
  CMPNY_NM varchar(180) NOT NULL,  -- 회사명
  CXFC varchar(150),  -- 대표자명
  ZIP varchar(18),  -- 우편번호
  ADRES varchar(300),  -- 주소
  ENTRPRS_MIDDLE_TELNO varchar(12),  -- 전화 중간번호
  FXNUM varchar(60),  -- 팩스
  INDUTY_CODE char(15),  -- 업종코드
  APPLCNT_NM varchar(150),  -- 신청자명
  APPLCNT_IHIDNUM varchar(39),  -- 신청자 주민등록번호
  SBSCRB_DE DATETIME,  -- 가입일시
  ENTRPRS_MBER_STTUS varchar(45),  -- 회원상태코드
  ENTRPRS_MBER_PASSWORD varchar(600) NOT NULL,  -- 비밀번호(해시)
  ENTRPRS_MBER_PASSWORD_HINT varchar(300) NOT NULL,  -- 비밀번호 힌트
  ENTRPRS_MBER_PASSWORD_CNSR varchar(300) NOT NULL,  -- 비밀번호 정답
  GROUP_ID char(20),  -- 그룹ID
  DETAIL_ADRES varchar(300),  -- 상세주소
  ENTRPRS_END_TELNO varchar(12),  -- 전화 끝번호
  AREA_NO varchar(12),  -- 지역번호
  APPLCNT_EMAIL_ADRES varchar(150),  -- 신청자 이메일
  TELNO varchar(60),  -- 전화번호
  ESNTL_ID char(20) NOT NULL,  -- 고유ID
  CONSTRAINT TB_ENTRPRS_MBER_PK PRIMARY KEY (ENTRPRS_MBER_ID)
) ;

CREATE TABLE TB_FILE (
  ATCH_FILE_ID char(20) NOT NULL,  -- 첨부파일 묶음ID
  CREAT_DT DATETIME NOT NULL,  -- 생성시각
  USE_AT char(1),  -- 사용여부(Y/N)
  CONSTRAINT TB_FILE_PK PRIMARY KEY (ATCH_FILE_ID)
) ;

CREATE TABLE TB_FILE_DETAIL (
  ATCH_FILE_ID char(20) NOT NULL,  -- 첨부파일 묶음ID
  FILE_SN numeric(10,0) NOT NULL,  -- 파일 일련번호
  FILE_STRE_COURS varchar(6000) NOT NULL,  -- 파일 저장경로
  STRE_FILE_NM varchar(765) NOT NULL,  -- 저장파일명
  ORIGNL_FILE_NM varchar(765),  -- 원파일명
  FILE_EXTSN varchar(60) NOT NULL,  -- 파일확장자
  FILE_CN STRING,
  FILE_SIZE numeric(8,0),  -- 파일크기(Byte)
  CONSTRAINT TB_FILE_DETAIL_PK PRIMARY KEY (ATCH_FILE_ID,FILE_SN)
) ;

CREATE TABLE TB_FAQ_INFO (
  FAQ_ID char(20) NOT NULL,  -- FAQID
  QESTN_SJ varchar(765),  -- 질문제목
  QESTN_CN varchar(7500),  -- 질문내용
  ANSWER_CN varchar(7500),  -- 답변내용
  RDCNT numeric(10,0),  -- 조회수
  FRST_REGIST_PNTTM DATETIME NOT NULL,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60) NOT NULL,  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME NOT NULL,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60) NOT NULL,  -- 최종수정자ID
  ATCH_FILE_ID char(20),  -- 첨부파일 묶음ID
  QNA_PROCESS_STTUS_CODE char(1),  -- 질의응답처리상태상세코드
  CONSTRAINT TB_FAQ_INFO_PK PRIMARY KEY (FAQ_ID)
) ;

CREATE TABLE TB_INDVDL_INFO_POLICY (
  INDVDL_INFO_POLICY_ID char(20) DEFAULT '' NOT NULL,  -- 개인정보정책ID
  INDVDL_INFO_POLICY_CN varchar(7500),  -- 개인정보정책내용
  INDVDL_INFO_POLICY_AGRE_AT char(1),  -- 개인정보정책동의여부
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  INDVDL_INFO_POLICY_NM varchar(765),  -- 개인정보정책명
  CONSTRAINT TB_INDVDL_INFO_POLICY_PK PRIMARY KEY (INDVDL_INFO_POLICY_ID)
) ;

CREATE TABLE TB_PROGRM_LIST (
  PROGRM_FILE_NM varchar(180) DEFAULT '' NOT NULL,  -- 프로그램파일명
  PROGRM_STRE_PATH varchar(300) NOT NULL,  -- 프로그램STRE경로
  PROGRM_KOREAN_NM varchar(180),  -- 프로그램KOREAN명
  PROGRM_DC varchar(600),  -- 프로그램설명
  URL varchar(300) NOT NULL,  -- URL
  CONSTRAINT TB_PROGRM_LIST_PK PRIMARY KEY (PROGRM_FILE_NM)
) ;

CREATE TABLE TB_MENU_INFO (
  MENU_NM varchar(180) NOT NULL,  -- 메뉴명
  PROGRM_FILE_NM varchar(180) NOT NULL,  -- 프로그램파일명
  MENU_NO numeric(20,0) NOT NULL,  -- 메뉴번호
  UPPER_MENU_NO numeric(20,0),  -- UPPER메뉴번호
  MENU_ORDR numeric(5,0) NOT NULL,  -- 메뉴순서
  MENU_DC varchar(750),  -- 메뉴설명
  RELATE_IMAGE_PATH varchar(300),  -- 관련이미지경로
  RELATE_IMAGE_NM varchar(180),  -- 관련이미지명
  CONSTRAINT TB_MENU_INFO_PK PRIMARY KEY (MENU_NO)
) ;

CREATE TABLE TB_MENU_CREAT_DTLS (
  MENU_NO numeric(20,0) NOT NULL,  -- 메뉴번호
  AUTHOR_CODE varchar(90) NOT NULL,  -- 권한코드
  MAPNG_CREAT_ID varchar(90),  -- MAPNG생성ID
  CONSTRAINT TB_MENU_CREAT_DTLS_PK PRIMARY KEY (MENU_NO,AUTHOR_CODE)
) ;

CREATE TABLE TB_QA_INFO (
  QA_ID char(20) NOT NULL,  -- 질의응답ID
  QESTN_SJ varchar(765),  -- 질문제목
  QESTN_CN varchar(7500),  -- 질문내용
  WRITNG_DE char(20),  -- 작성일자
  RDCNT numeric(10,0),  -- 조회수
  EMAIL_ADRES varchar(150),  -- 이메일
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  QNA_PROCESS_STTUS_CODE char(1),  -- 질의응답처리상태상세코드
  WRTER_NM varchar(60),  -- WRTER명
  ANSWER_CN varchar(7500),  -- 답변내용
  WRITNG_PASSWORD varchar(60),  -- 작성비밀번호(해시)
  ANSWER_DE char(20),  -- 답변일자
  EMAIL_ANSWER_AT char(1),  -- 이메일답변여부
  AREA_NO varchar(12),  -- 지역번호
  MIDDLE_TELNO varchar(12),  -- 전화 중간번호
  END_TELNO varchar(12),  -- 전화 끝번호
  CONSTRAINT TB_QA_INFO_PK PRIMARY KEY (QA_ID)
) ;

CREATE TABLE TB_QUSTNR_TMPLAT (
  QUSTNR_TMPLAT_ID char(20) NOT NULL,  -- 설문템플릿ID
  QUSTNR_TMPLAT_TY varchar(300),  -- 설문템플릿유형
  QUSTNR_TMPLAT_DC varchar(6000),  -- 설문템플릿설명
  QUSTNR_TMPLAT_PATH_NM varchar(300),  -- 설문템플릿경로명
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  QUSTNR_TMPLAT_IMAGE_INFO blob,  -- 설문템플릿이미지정보
  CONSTRAINT TB_QUSTNR_TMPLAT_PK PRIMARY KEY (QUSTNR_TMPLAT_ID)
) ;

CREATE TABLE TB_QESTNR_INFO (
  QUSTNR_TMPLAT_ID char(20) NOT NULL,  -- 설문템플릿ID
  QESTNR_ID char(20) NOT NULL,  -- QESTNRID
  QUSTNR_SJ varchar(765),  -- 설문제목
  QUSTNR_PURPS varchar(3000),  -- 설문목적
  QUSTNR_WRITNG_GUIDANCE_CN varchar(6000),  -- 설문작성안내내용
  QUSTNR_TRGET varchar(3000),  -- 설문대상
  QUSTNR_BGNDE char(20),  -- 설문시작일자
  QUSTNR_ENDDE char(20),  -- 설문종료일자
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  CONSTRAINT TB_QESTNR_INFO_PK PRIMARY KEY (QUSTNR_TMPLAT_ID,QESTNR_ID)
) ;

CREATE TABLE TB_QUSTNR_QESITM (
  QESTNR_ID char(20) NOT NULL,  -- QESTNRID
  QUSTNR_QESITM_ID char(20) NOT NULL,  -- 설문QESITMID
  QUSTNR_TMPLAT_ID char(20) NOT NULL,  -- 설문템플릿ID
  QESTN_SN numeric(10,0),  -- 질문일련번호
  QESTN_TY_CODE char(1),  -- 질문유형상세코드
  QESTN_CN varchar(7500),  -- 질문내용
  MXMM_CHOISE_CO numeric(5,0),  -- MXMMCHOISE수
  FRST_REGIST_PNTTM DATETIME NOT NULL,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60) NOT NULL,  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME NOT NULL,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60) NOT NULL,  -- 최종수정자ID
  CONSTRAINT TB_QUSTNR_QESITM_PK PRIMARY KEY (QESTNR_ID,QUSTNR_QESITM_ID,QUSTNR_TMPLAT_ID)
) ;

CREATE TABLE TB_QUSTNR_IEM (
  QUSTNR_TMPLAT_ID char(20) NOT NULL,  -- 설문템플릿ID
  QESTNR_ID char(20) NOT NULL,  -- QESTNRID
  QUSTNR_QESITM_ID char(20) NOT NULL,  -- 설문QESITMID
  QUSTNR_IEM_ID varchar(60) NOT NULL,  -- 설문항목ID
  IEM_SN numeric(5,0),  -- 항목일련번호
  IEM_CN varchar(3000),  -- 항목내용
  ETC_ANSWER_AT char(1),  -- 기타답변여부
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  CONSTRAINT TB_QUSTNR_IEM_PK PRIMARY KEY (QUSTNR_TMPLAT_ID,QESTNR_ID,QUSTNR_QESITM_ID,QUSTNR_IEM_ID)
) ;

CREATE TABLE TB_QUSTNR_RESPOND_INFO (
  QUSTNR_TMPLAT_ID char(20) NOT NULL,  -- 설문템플릿ID
  QESTNR_ID char(20) NOT NULL,  -- QESTNRID
  QUSTNR_RESPOND_ID char(20) NOT NULL,  -- 설문응답ID
  SEXDSTN_CODE char(1),  -- 성별코드
  OCCP_TY_CODE varchar(10),  -- 직업유형상세코드
  RESPOND_NM varchar(150),  -- 응답명
  BRTHDY char(20),  -- 생년월일
  AREA_NO varchar(12),  -- 지역번호
  MIDDLE_TELNO varchar(12),  -- 전화 중간번호
  END_TELNO varchar(12),  -- 전화 끝번호
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  CONSTRAINT TB_QUSTNR_RESPOND_INFO_PK PRIMARY KEY (QUSTNR_TMPLAT_ID,QESTNR_ID,QUSTNR_RESPOND_ID)
) ;

CREATE TABLE TB_QUSTNR_RSPNS_RESULT (
  QUSTNR_RSPNS_RESULT_ID char(20) NOT NULL,  -- 설문응답결과ID
  QESTNR_ID char(20) NOT NULL,  -- QESTNRID
  QUSTNR_QESITM_ID char(20) NOT NULL,  -- 설문QESITMID
  QUSTNR_TMPLAT_ID char(20) NOT NULL,  -- 설문템플릿ID
  RESPOND_ANSWER_CN varchar(3000),  -- 응답답변내용
  ETC_ANSWER_CN varchar(3000),  -- 기타답변내용
  RESPOND_NM varchar(150),  -- 응답명
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  QUSTNR_IEM_ID varchar(60),  -- 설문항목ID
  CONSTRAINT TB_QUSTNR_RSPNS_RESULT_PK PRIMARY KEY (QUSTNR_RSPNS_RESULT_ID,QESTNR_ID,QUSTNR_QESITM_ID,QUSTNR_TMPLAT_ID)
) ;

CREATE TABLE TB_STPLAT_INFO (
  USE_STPLAT_ID char(20) NOT NULL,  -- 이용약관ID
  USE_STPLAT_NM varchar(300),  -- 이용약관명
  USE_STPLAT_CN STRING,
  INFO_PROVD_AGRE_CN STRING,
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  CONSTRAINT TB_STPLAT_INFO_PK PRIMARY KEY (USE_STPLAT_ID)
) ;

CREATE TABLE TB_SCHDUL_INFO (
  SCHDUL_ID char(20) NOT NULL,  -- 일정ID
  SCHDUL_SE char(1),  -- 일정구분코드
  SCHDUL_DEPT_ID varchar(20),  -- 부서ID
  SCHDUL_KND_CODE varchar(20),  -- 일정종류코드
  SCHDUL_BEGINDE DATETIME,  -- 시작일시
  SCHDUL_ENDDE DATETIME,  -- 종료일시
  SCHDUL_NM varchar(255),  -- 일정명
  SCHDUL_CN varchar(2500),  -- 일정 내용
  SCHDUL_PLACE varchar(255),  -- 장소
  SCHDUL_IPCR_CODE char(1),  -- 중요도코드
  SCHDUL_CHARGER_ID varchar(20),  -- 담당자ID
  ATCH_FILE_ID char(20),  -- 첨부파일 묶음ID
  REPTIT_SE_CODE char(3),  -- 반복구분코드
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  CONSTRAINT TB_SCHDUL_INFO_PK PRIMARY KEY (SCHDUL_ID)
) ;

CREATE TABLE TB_RESTDE (
  RESTDE_NO numeric(20,0) NOT NULL,  -- 휴일번호
  RESTDE_DE char(8) NOT NULL,  -- 휴일일자
  RESTDE_NM varchar(100) NOT NULL,  -- 휴일명
  RESTDE_DC varchar(200),  -- 휴일설명
  RESTDE_SE_CODE char(6),  -- 휴일구분상세코드
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(60),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(60),  -- 최종수정자ID
  CONSTRAINT TB_RESTDE_PK PRIMARY KEY (RESTDE_NO)
) ;

CREATE OR REPLACE VIEW VW_USER_MASTER ( ESNTL_ID,USER_ID,PASSWORD,USER_NM,USER_ZIP,USER_ADRES,USER_EMAIL,GROUP_ID, USER_SE, ORGNZT_ID ) 
AS  
        SELECT ESNTL_ID, MBER_ID,PASSWORD,MBER_NM,ZIP,ADRES,MBER_EMAIL_ADRES,' ','GNR' AS USER_SE, ' ' ORGNZT_ID
        FROM TB_GNRL_MBER
    UNION ALL
        SELECT ESNTL_ID,EMPLYR_ID,PASSWORD,USER_NM,ZIP,HOUSE_ADRES,EMAIL_ADRES,GROUP_ID ,'USR' AS USER_SE, ORGNZT_ID
        FROM TB_EMPLYR_INFO
    UNION ALL
        SELECT ESNTL_ID,ENTRPRS_MBER_ID,ENTRPRS_MBER_PASSWORD,CMPNY_NM,ZIP,ADRES,APPLCNT_EMAIL_ADRES,' ' ,'ENT' AS USER_SE, ' ' ORGNZT_ID
        FROM TB_ENTRPRS_MBER 
;

-- ============================================================
-- enterprise-biz 전용 테이블 (로그/정책/부재) — shtdb.sql 기준 현행화
-- ============================================================
CREATE TABLE TB_LOGIN_LOG  (
  LOG_ID char(20) NOT NULL,  -- 로그ID
  CONECT_MTHD char(1),  -- 접속방식
  CONECT_ID varchar(20),  -- 접속ID
  CONECT_IP varchar(23),  -- 접속IP
  ERROR_OCCRRNC_AT char(1),  -- 오류발생여부
  ERROR_CODE varchar(3),  -- 오류상세코드
  CREAT_DT DATETIME,  -- 생성시각
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(20),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(20),  -- 최종수정자ID
  CONSTRAINT TB_LOGIN_LOG_PK PRIMARY KEY (LOG_ID)
) ;

CREATE TABLE TB_SYS_LOG  (
  REQUST_ID char(20) NOT NULL,  -- 요청ID
  OCCRRNC_DE char(20),  -- 발생일자
  SVC_NM varchar(200),  -- 봉사명
  METHOD_NM varchar(60),  -- METHOD명
  PROCESS_SE_CODE varchar(15),  -- 처리구분상세코드
  PROCESS_TIME varchar(14),  -- 처리시간
  RQESTER_ID varchar(20),  -- RQESTERID
  RQESTER_IP varchar(23),  -- RQESTERIP
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(20),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(20),  -- 최종수정자ID
  CONSTRAINT TB_SYS_LOG_PK PRIMARY KEY (REQUST_ID)
) ;

CREATE TABLE TB_SYS_LOG_SUMMARY  (
  OCCRRNC_DE char(20) NOT NULL,  -- 발생일자
  SVC_NM varchar(200) NOT NULL,  -- 봉사명
  METHOD_NM varchar(60) NOT NULL,  -- METHOD명
  CREAT_CO numeric(10,0),  -- 생성수
  UPDT_CO numeric(10,0),  -- 갱신수
  RDCNT numeric(10,0),  -- 조회수
  DELETE_CO numeric(10,0),  -- 삭제수
  OUTPT_CO numeric(10,0),  -- OUTPT수
  ERROR_CO numeric(10,0),  -- 오류수
  CONSTRAINT TB_SYS_LOG_SUMMARY_PK PRIMARY KEY (OCCRRNC_DE,SVC_NM,METHOD_NM)
) ;

CREATE TABLE TB_CONECT_LOG  (
  LOG_ID numeric(20,0) DEFAULT 0 NOT NULL,  -- 로그ID
  SRVC_NM varchar(60),  -- 서비스명
  METHOD_NM varchar(60),  -- METHOD명
  OCCRRNC_DE char(8),  -- 발생일자
  CREAT_CO numeric(10,0) DEFAULT 0,  -- 생성수
  UPDT_CO numeric(10,0) DEFAULT 0,  -- 갱신수
  RDCNT numeric(10,0) DEFAULT 0,  -- 조회수
  DELETE_CO numeric(10,0) DEFAULT 0,  -- 삭제수
  OUTPT_CO numeric(10,0) DEFAULT 0,  -- OUTPT수
  ERROR_CO numeric(10,0) DEFAULT 0,  -- 오류수
  CONECT_ID varchar(20),  -- 접속ID
  CONECT_IP varchar(23),  -- 접속IP
  CONECT_MTHD char(1),  -- 접속방식
  ERROR_OCCRRNC_AT char(1),  -- 오류발생여부
  ERROR_CODE varchar(3),  -- 오류상세코드
  CREAT_DT DATETIME,  -- 생성시각
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(20),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(20),  -- 최종수정자ID
  CONSTRAINT TB_CONECT_LOG_PK PRIMARY KEY (LOG_ID)
) ;

CREATE TABLE TB_LOGIN_POLICY  (
  EMPLYR_ID varchar(20) NOT NULL,  -- 사용자ID
  IP_INFO varchar(23),  -- IP정보
  DPLCT_PERM_AT char(1) DEFAULT 'Y',  -- DPLCTPERM여부
  LMTT_AT char(1) DEFAULT 'N',  -- LMTT여부
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(20),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(20),  -- 최종수정자ID
  CONSTRAINT TB_LOGIN_POLICY_PK PRIMARY KEY (EMPLYR_ID)
) ;

CREATE TABLE TB_USER_ABSNCE  (
  EMPLYR_ID varchar(20) NOT NULL,  -- 사용자ID
  USER_ABSNCE_AT char(1) DEFAULT 'N',  -- 사용자ABSNCE여부
  FRST_REGIST_PNTTM DATETIME,  -- 최초등록시각
  FRST_REGISTER_ID varchar(20),  -- 최초등록자ID
  LAST_UPDT_PNTTM DATETIME,  -- 최종수정시각
  LAST_UPDUSR_ID varchar(20),  -- 최종수정자ID
  CONSTRAINT TB_USER_ABSNCE_PK PRIMARY KEY (EMPLYR_ID)
) ;

-- 프로그램 변경요청/처리/이력 (enterprise-biz-boot 추가)
CREATE TABLE TB_PROGRM_CHANGE_DTLS (
  PROGRM_FILE_NM     VARCHAR(180) NOT NULL,  -- 프로그램파일명
  REQUST_NO          NUMERIC(10)  DEFAULT 0 NOT NULL,  -- 요청번호
  PROGRM_KOREAN_NM   VARCHAR(60),  -- 프로그램KOREAN명
  RQESTER_ID         VARCHAR(20),  -- RQESTERID
  CHANGE_REQUST_CN   VARCHAR(2500),  -- 변경요청내용
  REQUST_PROCESS_CN  VARCHAR(2500),  -- 요청처리내용
  OPETR_ID           VARCHAR(20),  -- OPETRID
  PROCESS_STTUS_CODE VARCHAR(15),  -- 처리상태상세코드
  PROCESS_DE         VARCHAR(20),  -- 처리일자
  REQUST_DE          VARCHAR(20),  -- 요청일자
  REQUST_SJ          VARCHAR(100),  -- 요청제목
  PRIMARY KEY (PROGRM_FILE_NM, REQUST_NO)
);

-- ============================================================
-- 포털 패리티: 배너 유형/팝업/게시기간 + 약관·처리방침 버전/대표/사용여부 컬럼 보강
-- (매퍼 selectBannerListByType / selectRepresentStplat 등이 참조)
-- ============================================================
ALTER TABLE TB_BANNER ADD COLUMN BANNER_TY VARCHAR(10) DEFAULT 'MAIN';
ALTER TABLE TB_BANNER ADD COLUMN POPUP_LEFT INTEGER;
ALTER TABLE TB_BANNER ADD COLUMN POPUP_TOP INTEGER;
ALTER TABLE TB_BANNER ADD COLUMN POPUP_WIDTH INTEGER;
ALTER TABLE TB_BANNER ADD COLUMN POPUP_HEIGHT INTEGER;
ALTER TABLE TB_BANNER ADD COLUMN POPUP_GROUP_AT CHAR(1) DEFAULT 'N';
ALTER TABLE TB_BANNER ADD COLUMN EXPSR_BGNDE DATE;
ALTER TABLE TB_BANNER ADD COLUMN EXPSR_ENDDE DATE;
ALTER TABLE TB_STPLAT_INFO ADD COLUMN VER VARCHAR(20);
ALTER TABLE TB_STPLAT_INFO ADD COLUMN APLC_DE VARCHAR(8);
ALTER TABLE TB_STPLAT_INFO ADD COLUMN REPRSNT_AT CHAR(1) DEFAULT 'N';
ALTER TABLE TB_STPLAT_INFO ADD COLUMN USE_AT CHAR(1) DEFAULT 'Y';
ALTER TABLE TB_INDVDL_INFO_POLICY ADD COLUMN VER VARCHAR(20);
ALTER TABLE TB_INDVDL_INFO_POLICY ADD COLUMN APLC_DE VARCHAR(8);
ALTER TABLE TB_INDVDL_INFO_POLICY ADD COLUMN REPRSNT_AT CHAR(1) DEFAULT 'N';
ALTER TABLE TB_INDVDL_INFO_POLICY ADD COLUMN USE_AT CHAR(1) DEFAULT 'Y';
