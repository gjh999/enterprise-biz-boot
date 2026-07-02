# DB 컬럼 한글명 매핑

> 6종 DBMS DDL의 인라인 한글 논리명 주석에서 추출한 매핑표. 물리 컬럼명 → 한글 논리명.


## IDS

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| TABLE_NAME | 대상 테이블명 |
| NEXT_ID | 다음 ID 값 |

## COMTECOPSEQ

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| TABLE_NAME | 대상 테이블명 |
| NEXT_ID | 다음 ID 값 |

## TB_CMMN_CL_CODE

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| CL_CODE | 분류코드 |
| CL_CODE_NM | 분류코드명 |
| CL_CODE_DC | 분류코드 설명 |
| USE_AT | 사용여부(Y/N) |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_CMMN_CODE

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| CODE_ID | 코드ID |
| CODE_ID_NM | 코드ID명 |
| CODE_ID_DC | 코드ID 설명 |
| USE_AT | 사용여부(Y/N) |
| CL_CODE | 분류코드 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_CMMN_DETAIL_CODE

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| CODE_ID | 코드ID |
| CODE | 상세코드 |
| CODE_NM | 상세코드명 |
| CODE_DC | 상세코드 설명 |
| USE_AT | 사용여부(Y/N) |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_AUTHOR_INFO

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| AUTHOR_CODE | 권한코드 |
| AUTHOR_NM | 권한명 |
| AUTHOR_DC | 권한 설명 |
| AUTHOR_CREAT_DE | 생성일자 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_AUTHOR_GROUP_INFO

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| GROUP_ID | 그룹ID |
| GROUP_NM | 그룹명 |
| GROUP_CREAT_DE | 생성일자 |
| GROUP_DC | 그룹 설명 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_ORGNZT_INFO

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| ORGNZT_ID | 조직ID |
| ORGNZT_NM | 조직명 |
| ORGNZT_DC | 조직 설명 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_EMPLYR_INFO

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| EMPLYR_ID | 사용자ID |
| ORGNZT_ID | 조직ID |
| USER_NM | 사용자명 |
| PASSWORD | 비밀번호(해시) |
| EMPL_NO | 사번 |
| IHIDNUM | 주민등록번호 |
| SEXDSTN_CODE | 성별코드 |
| BRTHDY | 생년월일 |
| FXNUM | 팩스 |
| HOUSE_ADRES | 자택주소 |
| PASSWORD_HINT | 비밀번호 힌트 |
| PASSWORD_CNSR | 비밀번호 정답 |
| HOUSE_END_TELNO | 자택전화 끝번호 |
| AREA_NO | 지역번호 |
| DETAIL_ADRES | 상세주소 |
| ZIP | 우편번호 |
| OFFM_TELNO | 사무실전화 |
| MBTLNUM | 휴대전화 |
| EMAIL_ADRES | 이메일 |
| OFCPS_NM | 직책명 |
| HOUSE_MIDDLE_TELNO | 자택전화 중간번호 |
| GROUP_ID | 그룹ID |
| PSTINST_CODE | 소속기관코드 |
| EMPLYR_STTUS_CODE | 사용자상태코드 |
| ESNTL_ID | 고유ID |
| CRTFC_DN_VALUE | 인증DN |
| SBSCRB_DE | 가입일시 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_EMPLYR_INFO_CHNG_DTLS

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| EMPLYR_ID | 사용자ID |
| CHANGE_DE | 변경일자 |
| ORGNZT_ID | 조직ID |
| GROUP_ID | 그룹ID |
| EMPL_NO | 사번 |
| SEXDSTN_CODE | 성별코드 |
| BRTHDY | 생년월일 |
| FXNUM | 팩스 |
| HOUSE_ADRES | 자택주소 |
| HOUSE_END_TELNO | 자택전화 끝번호 |
| AREA_NO | 지역번호 |
| DETAIL_ADRES | 상세주소 |
| ZIP | 우편번호 |
| OFFM_TELNO | 사무실전화 |
| MBTLNUM | 휴대전화 |
| EMAIL_ADRES | 이메일 |
| HOUSE_MIDDLE_TELNO | 자택전화 중간번호 |
| PSTINST_CODE | 소속기관코드 |
| EMPLYR_STTUS_CODE | 사용자상태코드 |
| ESNTL_ID | 고유ID |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_EMPLYR_SCRTY_ESTBS

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| SCRTY_DTRMN_TRGET_ID | 보안결정대상ID(보통 사용자ID) |
| MBER_TY_CODE | 회원유형코드 |
| AUTHOR_CODE | 권한코드 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_GNRL_MBER

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| MBER_ID | 회원ID |
| PASSWORD | 비밀번호(해시) |
| PASSWORD_HINT | 비밀번호 힌트 |
| PASSWORD_CNSR | 비밀번호 정답 |
| IHIDNUM | 주민등록번호 |
| MBER_NM | 회원명 |
| ZIP | 우편번호 |
| ADRES | 주소 |
| AREA_NO | 지역번호 |
| MBER_STTUS | 회원상태코드 |
| DETAIL_ADRES | 상세주소 |
| END_TELNO | 전화 끝번호 |
| MBTLNUM | 휴대전화 |
| GROUP_ID | 그룹ID |
| MBER_FXNUM | 팩스번호 |
| MBER_EMAIL_ADRES | 이메일주소 |
| MIDDLE_TELNO | 전화 중간번호 |
| SBSCRB_DE | 가입일시 |
| SEXDSTN_CODE | 성별코드 |
| ESNTL_ID | 고유ID |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_ENTRPRS_MBER

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| ENTRPRS_MBER_ID | 기업회원ID |
| ENTRPRS_SE_CODE | 기업구분코드 |
| BIZRNO | 사업자등록번호 |
| JURIRNO | 법인등록번호 |
| CMPNY_NM | 회사명 |
| CXFC | 대표자명 |
| ZIP | 우편번호 |
| ADRES | 주소 |
| ENTRPRS_MIDDLE_TELNO | 전화 중간번호 |
| FXNUM | 팩스 |
| INDUTY_CODE | 업종코드 |
| APPLCNT_NM | 신청자명 |
| APPLCNT_IHIDNUM | 신청자 주민등록번호 |
| SBSCRB_DE | 가입일시 |
| ENTRPRS_MBER_STTUS | 회원상태코드 |
| ENTRPRS_MBER_PASSWORD | 비밀번호(해시) |
| ENTRPRS_MBER_PASSWORD_HINT | 비밀번호 힌트 |
| ENTRPRS_MBER_PASSWORD_CNSR | 비밀번호 정답 |
| GROUP_ID | 그룹ID |
| DETAIL_ADRES | 상세주소 |
| ENTRPRS_END_TELNO | 전화 끝번호 |
| AREA_NO | 지역번호 |
| APPLCNT_EMAIL_ADRES | 신청자 이메일 |
| TELNO | 전화번호 |
| ESNTL_ID | 고유ID |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_BBS_MASTER

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| BBS_ID | 게시판ID |
| BBS_NM | 게시판명 |
| BBS_INTRCN | 게시판 소개 |
| BBS_TY_CODE | 게시판 유형코드 |
| BBS_ATTRB_CODE | 게시판 속성코드 |
| REPLY_POSBL_AT | 답글 가능(Y/N) |
| FILE_ATCH_POSBL_AT | 파일첨부 가능(Y/N) |
| ATCH_POSBL_FILE_NUMBER | 첨부가능 파일 수 |
| ATCH_POSBL_FILE_SIZE | 첨부가능 총 용량(단위: 시스템 정의) |
| USE_AT | 사용여부(Y/N) |
| TMPLAT_ID | 템플릿ID |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_BBS_MASTER_OPTN

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| BBS_ID | 게시판ID |
| ANSWER_AT | 답변글 여부(Y/N) |
| STSFDG_AT | 만족도 조사 사용(Y/N) |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_BBS_USE

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| BBS_ID | 게시판ID |
| TRGET_ID | 사용대상ID |
| USE_AT | 사용여부(Y/N) |
| REGIST_SE_CODE | 등록구분코드 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_BBS

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| NTT_ID | 게시물ID |
| BBS_ID | 게시판ID |
| NTT_NO | 게시물 번호(정렬/표시용) |
| NTT_SJ | 제목 |
| NTT_CN | 내용 |
| ANSWER_AT | 답변글 여부(Y/N) |
| PARNTSCTT_NO | 부모글 번호 |
| ANSWER_LC | 답변 계층(레벨) |
| SORT_ORDR | 정렬순서 |
| RDCNT | 조회수 |
| USE_AT | 사용여부(Y/N) |
| NTCE_BGNDE | 공지 시작일시 |
| NTCE_ENDDE | 공지 종료일시 |
| NTCR_ID | 게시자ID |
| NTCR_NM | 게시자명 |
| PASSWORD | 비밀번호(해시) |
| ATCH_FILE_ID | 첨부파일 묶음ID |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_FILE

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| ATCH_FILE_ID | 첨부파일 묶음ID |
| CREAT_DT | 생성시각 |
| USE_AT | 사용여부(Y/N) |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_FILE_DETAIL

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| ATCH_FILE_ID | 첨부파일 묶음ID |
| FILE_SN | 파일 일련번호 |
| FILE_STRE_COURS | 파일 저장경로 |
| STRE_FILE_NM | 저장파일명 |
| ORIGNL_FILE_NM | 원파일명 |
| FILE_EXTSN | 파일확장자 |
| FILE_CN | 파일 내용/비고 |
| FILE_SIZE | 파일크기(Byte) |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_SCHDUL_INFO

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| SCHDUL_ID | 일정ID |
| SCHDUL_SE | 일정구분코드 |
| SCHDUL_DEPT_ID | 부서ID |
| SCHDUL_KND_CODE | 일정종류코드 |
| SCHDUL_BEGINDE | 시작일시 |
| SCHDUL_ENDDE | 종료일시 |
| SCHDUL_NM | 일정명 |
| SCHDUL_CN | 일정 내용 |
| SCHDUL_PLACE | 장소 |
| SCHDUL_IPCR_CODE | 중요도코드 |
| SCHDUL_CHARGER_ID | 담당자ID |
| ATCH_FILE_ID | 첨부파일 묶음ID |
| REPTIT_SE_CODE | 반복구분코드 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_RESTDE

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| RESTDE_NO | 휴일번호 |
| RESTDE_DE | 휴일일자 |
| RESTDE_NM | 휴일명 |
| RESTDE_DC | 휴일설명 |
| RESTDE_SE_CODE | 휴일구분상세코드 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_FAQ_INFO

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| FAQ_ID | FAQID |
| QESTN_SJ | 질문제목 |
| QESTN_CN | 질문내용 |
| ANSWER_CN | 답변내용 |
| RDCNT | 조회수 |
| ATCH_FILE_ID | 첨부파일 묶음ID |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_QA_INFO

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| QA_ID | 질의응답ID |
| QESTN_SJ | 질문제목 |
| QESTN_CN | 질문내용 |
| WRITNG_PASSWORD | 작성비밀번호(해시) |
| AREA_NO | 지역번호 |
| MIDDLE_TELNO | 전화 중간번호 |
| END_TELNO | 전화 끝번호 |
| EMAIL_ADRES | 이메일 |
| EMAIL_ANSWER_AT | 이메일답변여부 |
| WRTER_NM | WRTER명 |
| WRITNG_DE | 작성일자 |
| RDCNT | 조회수 |
| QNA_PROCESS_STTUS_CODE | 질의응답처리상태상세코드 |
| ANSWER_CN | 답변내용 |
| ANSWER_DE | 답변일자 |
| ATCH_FILE_ID | 첨부파일 묶음ID |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_TMPLAT_INFO

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| TMPLAT_ID | 템플릿ID |
| TMPLAT_NM | 템플릿명 |
| TMPLAT_COURS | 템플릿COURS |
| TMPLAT_SE_CODE | 템플릿구분상세코드 |
| USE_AT | 사용여부(Y/N) |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_ROLES_HIERARCHY

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| PARNTS_ROLE | 부모롤 |
| CHLDRN_ROLE | 자식롤 |

## TB_ROLE_INFO

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| ROLE_CODE | 역할코드 |
| ROLE_NM | 역할명 |
| ROLE_PTTRN | 롤PTTRN |
| ROLE_DC | 역할설명 |
| ROLE_TY | 역할유형 |
| ROLE_SORT | 역할정렬 |
| ROLE_CREAT_DE | 롤생성일자 |

## TB_AUTHOR_ROLE_RELATE

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| AUTHOR_CODE | 권한코드 |
| ROLE_CODE | 역할코드 |
| CREAT_DT | 생성시각 |

## TB_ZIP

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| ZIP | 우편번호 |
| SN | 일련번호 |
| CTPRVN_NM | 시도명 |
| SIGNGU_NM | 시군구명 |
| EMD_NM | 읍면동명 |
| LI_BULD_NM | 리건물명 |
| LNBR_DONG_HO | 지번동호 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_BANNER

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| BANNER_ID | 배너ID |
| BANNER_NM | 배너명 |
| LINK_URL | 연계URL |
| BANNER_IMAGE | 배너이미지 |
| BANNER_DC | 배너설명 |
| REFLCT_AT | REFLCT여부 |
| FRST_REGISTER_ID | 최초등록자ID |
| FRST_REGIST_PNTTM | 최초등록시각 |
| LAST_UPDUSR_ID | 최종수정자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| BANNER_IMAGE_FILE | 배너이미지파일 |
| SORT_ORDR | 정렬순서 |

## TB_INDVDL_INFO_POLICY

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| INDVDL_INFO_POLICY_ID | 개인정보정책ID |
| INDVDL_INFO_POLICY_CN | 개인정보정책내용 |
| INDVDL_INFO_POLICY_AGRE_AT | 개인정보정책동의여부 |
| FRST_REGISTER_ID | 최초등록자ID |
| FRST_REGIST_PNTTM | 최초등록시각 |
| LAST_UPDUSR_ID | 최종수정자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| INDVDL_INFO_POLICY_NM | 개인정보정책명 |

## TB_PROGRM_LIST

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| PROGRM_FILE_NM | 프로그램파일명 |
| PROGRM_STRE_PATH | 프로그램STRE경로 |
| PROGRM_KOREAN_NM | 프로그램KOREAN명 |
| PROGRM_DC | 프로그램설명 |
| URL | URL |

## TB_MENU_INFO

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| MENU_NM | 메뉴명 |
| PROGRM_FILE_NM | 프로그램파일명 |
| MENU_NO | 메뉴번호 |
| UPPER_MENU_NO | UPPER메뉴번호 |
| MENU_ORDR | 메뉴순서 |
| MENU_DC | 메뉴설명 |
| RELATE_IMAGE_PATH | 관련이미지경로 |
| RELATE_IMAGE_NM | 관련이미지명 |

## TB_MENU_CREAT_DTLS

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| MENU_NO | 메뉴번호 |
| AUTHOR_CODE | 권한코드 |
| MAPNG_CREAT_ID | MAPNG생성ID |

## TB_QUSTNR_TMPLAT

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| QUSTNR_TMPLAT_ID | 설문템플릿ID |
| QUSTNR_TMPLAT_TY | 설문템플릿유형 |
| QUSTNR_TMPLAT_DC | 설문템플릿설명 |
| QUSTNR_TMPLAT_PATH_NM | 설문템플릿경로명 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |
| QUSTNR_TMPLAT_IMAGE_INFO | 설문템플릿이미지정보 |

## TB_QESTNR_INFO

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| QUSTNR_TMPLAT_ID | 설문템플릿ID |
| QESTNR_ID | QESTNRID |
| QUSTNR_SJ | 설문제목 |
| QUSTNR_PURPS | 설문목적 |
| QUSTNR_WRITNG_GUIDANCE_CN | 설문작성안내내용 |
| QUSTNR_TRGET | 설문대상 |
| QUSTNR_BGNDE | 설문시작일자 |
| QUSTNR_ENDDE | 설문종료일자 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_QUSTNR_QESITM

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| QESTNR_ID | QESTNRID |
| QUSTNR_QESITM_ID | 설문QESITMID |
| QUSTNR_TMPLAT_ID | 설문템플릿ID |
| QESTN_SN | 질문일련번호 |
| QESTN_TY_CODE | 질문유형상세코드 |
| QESTN_CN | 질문내용 |
| MXMM_CHOISE_CO | MXMMCHOISE수 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_QUSTNR_IEM

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| QUSTNR_TMPLAT_ID | 설문템플릿ID |
| QESTNR_ID | QESTNRID |
| QUSTNR_QESITM_ID | 설문QESITMID |
| QUSTNR_IEM_ID | 설문항목ID |
| IEM_SN | 항목일련번호 |
| IEM_CN | 항목내용 |
| ETC_ANSWER_AT | 기타답변여부 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_QUSTNR_RESPOND_INFO

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| QUSTNR_TMPLAT_ID | 설문템플릿ID |
| QESTNR_ID | QESTNRID |
| QUSTNR_RESPOND_ID | 설문응답ID |
| SEXDSTN_CODE | 성별코드 |
| OCCP_TY_CODE | 직업유형상세코드 |
| RESPOND_NM | 응답명 |
| BRTHDY | 생년월일 |
| AREA_NO | 지역번호 |
| MIDDLE_TELNO | 전화 중간번호 |
| END_TELNO | 전화 끝번호 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_QUSTNR_RSPNS_RESULT

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| QUSTNR_RSPNS_RESULT_ID | 설문응답결과ID |
| QESTNR_ID | QESTNRID |
| QUSTNR_QESITM_ID | 설문QESITMID |
| QUSTNR_TMPLAT_ID | 설문템플릿ID |
| RESPOND_ANSWER_CN | 응답답변내용 |
| ETC_ANSWER_CN | 기타답변내용 |
| RESPOND_NM | 응답명 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |
| QUSTNR_IEM_ID | 설문항목ID |

## TB_STPLAT_INFO

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| USE_STPLAT_ID | 이용약관ID |
| USE_STPLAT_NM | 이용약관명 |
| USE_STPLAT_CN | 이용약관 내용 |
| INFO_PROVD_AGRE_CN | 정보제공 동의 내용 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_LOGIN_LOG

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| LOG_ID | 로그ID |
| CONECT_MTHD | 접속방식 |
| CONECT_ID | 접속ID |
| CONECT_IP | 접속IP |
| ERROR_OCCRRNC_AT | 오류발생여부 |
| ERROR_CODE | 오류상세코드 |
| CREAT_DT | 생성시각 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_SYS_LOG

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| REQUST_ID | 요청ID |
| OCCRRNC_DE | 발생일자 |
| SVC_NM | 봉사명 |
| METHOD_NM | METHOD명 |
| PROCESS_SE_CODE | 처리구분상세코드 |
| PROCESS_TIME | 처리시간 |
| RQESTER_ID | RQESTERID |
| RQESTER_IP | RQESTERIP |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_SYS_LOG_SUMMARY

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| OCCRRNC_DE | 발생일자 |
| SVC_NM | 봉사명 |
| METHOD_NM | METHOD명 |
| CREAT_CO | 생성수 |
| UPDT_CO | 갱신수 |
| RDCNT | 조회수 |
| DELETE_CO | 삭제수 |
| OUTPT_CO | OUTPT수 |
| ERROR_CO | 오류수 |

## TB_CONECT_LOG

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| LOG_ID | 로그ID |
| SRVC_NM | 서비스명 |
| METHOD_NM | METHOD명 |
| OCCRRNC_DE | 발생일자 |
| CREAT_CO | 생성수 |
| UPDT_CO | 갱신수 |
| RDCNT | 조회수 |
| DELETE_CO | 삭제수 |
| OUTPT_CO | OUTPT수 |
| ERROR_CO | 오류수 |
| CONECT_ID | 접속ID |
| CONECT_IP | 접속IP |
| CONECT_MTHD | 접속방식 |
| ERROR_OCCRRNC_AT | 오류발생여부 |
| ERROR_CODE | 오류상세코드 |
| CREAT_DT | 생성시각 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_LOGIN_POLICY

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| EMPLYR_ID | 사용자ID |
| IP_INFO | IP정보 |
| DPLCT_PERM_AT | DPLCTPERM여부 |
| LMTT_AT | LMTT여부 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_USER_ABSNCE

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| EMPLYR_ID | 사용자ID |
| USER_ABSNCE_AT | 사용자ABSNCE여부 |
| FRST_REGIST_PNTTM | 최초등록시각 |
| FRST_REGISTER_ID | 최초등록자ID |
| LAST_UPDT_PNTTM | 최종수정시각 |
| LAST_UPDUSR_ID | 최종수정자ID |

## TB_PROGRM_CHANGE_DTLS

| 물리 컬럼명 | 한글 논리명 |
|---|---|
| PROGRM_FILE_NM | 프로그램파일명 |
| REQUST_NO | 요청번호 |
| PROGRM_KOREAN_NM | 프로그램KOREAN명 |
| RQESTER_ID | RQESTERID |
| CHANGE_REQUST_CN | 변경요청내용 |
| REQUST_PROCESS_CN | 요청처리내용 |
| OPETR_ID | OPETRID |
| PROCESS_STTUS_CODE | 처리상태상세코드 |
| PROCESS_DE | 처리일자 |
| REQUST_DE | 요청일자 |
| REQUST_SJ | 요청제목 |
