package megacom;

import megacom.service.StudSubjectService;
import megacom.service.impl.StudSubjectServiceImpl;

public class Main {

    public static void main(String[] args) {
        StudSubjectService studSubjectService = new StudSubjectServiceImpl();
        studSubjectService.ConnectionSQLite();
        studSubjectService.addSubject();
        studSubjectService.selectSubject();
        studSubjectService.close();
    }
}
