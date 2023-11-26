package fit.iuh.duongdinhlong20122841_week1.services;

import fit.iuh.duongdinhlong20122841_week1.models.Account;
import fit.iuh.duongdinhlong20122841_week1.models.GrantAccess;
import fit.iuh.duongdinhlong20122841_week1.models.Log;
import fit.iuh.duongdinhlong20122841_week1.models.Role;
import fit.iuh.duongdinhlong20122841_week1.repositories.AccountRepository;
import fit.iuh.duongdinhlong20122841_week1.repositories.GrantAccessRepository;
import fit.iuh.duongdinhlong20122841_week1.repositories.LogRepository;
import fit.iuh.duongdinhlong20122841_week1.repositories.RoleRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountService {
    private AccountRepository accountRepository;
    private GrantAccessRepository grantAccessRepository;
    private LogRepository logRepository;
    private RoleRepository roleRepository;

    public AccountService() throws SQLException, ClassNotFoundException {
        this.accountRepository = new AccountRepository();
        this.grantAccessRepository = new GrantAccessRepository();
        this.logRepository = new LogRepository();
        this.roleRepository = new RoleRepository();
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Optional<Account> result = accountRepository.findByEmailAndPassword(email, password);
        if(!result.isPresent()){
            req.setAttribute("error", "Bad credentials");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
        else{
            Account currentAccount = result.get();
            Log log = new Log((long)new Date().getTime(), currentAccount.getId(),  "");
            List<GrantAccess> grantAccesses = this.grantAccessRepository.getGrantAccessByAccountId(currentAccount.getId());
            this.logRepository.insertLog(log);
            boolean isAdmin = false;
            for(GrantAccess grantAccess : grantAccesses){
                Optional<Role> optionalRole = this.roleRepository.findRoleById(grantAccess.getRoleId());
                currentAccount.addRole(optionalRole.get());
                if(grantAccess.getRole().getId().equalsIgnoreCase("admin") && grantAccess.getIsGrant() == 1){
                    isAdmin = true;
                }
            }

            if(isAdmin){
                List<Account> accounts = this.accountRepository.getAll();
                req.getSession().setAttribute("account", currentAccount);
                req.getSession().setAttribute("log", log);

                req.setAttribute("accounts", accounts);
                req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
            }
            else{
                req.getSession().setAttribute("account", currentAccount);
                req.getSession().setAttribute("log", log);
                req.getRequestDispatcher("profile.jsp").forward(req, resp);
            }

        }
    }

    public void logout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Log log = (Log) req.getSession().getAttribute("log");
        this.logRepository.updateLogoutTime(log.getId());
        req.getRequestDispatcher("login.jsp").forward(req, res);
    }

    public void addAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String accountId = req.getParameter("accountId");
        String fullName = req.getParameter("fullName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String[] selectedRoles = req.getParameterValues("roles");

        Account account = new Account();
        account.setId(accountId);
        account.setPhone(phone);
        account.setFullName(fullName);
        account.setPassword(password);
        account.setEmail(email);

        if(this.accountRepository.addAccount(account)){
            for(int i = 0; i < selectedRoles.length; i++){
                GrantAccess grantAccess = new GrantAccess(1, "", roleRepository.findRoleById(selectedRoles), account);
                System.out.println(grantAccess);
                grantAccessRepository.addGrantAccess(grantAccess);
            }
            List<Account> accounts = this.accountRepository.getAll();
            Account currAccount = (Account) req.getSession().getAttribute("account");
            req.setAttribute("account", currAccount);
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("dashboard.jsp").forward(req, res);
        }
    }

    public void showAddAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Role> roles = this.roleRepository.getAll();
        req.setAttribute("roles", roles);
        req.getRequestDispatcher("account_form.jsp").forward(req, res);
    }

    public void deleteUser(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String accountId = req.getParameter("accountId");
        Account currentAccount = (Account) req.getSession().getAttribute("account");
        if(accountId.equalsIgnoreCase(currentAccount.getId())){
            req.setAttribute("error", "Can't delete yourself");
            List<Account> accounts = this.accountRepository.getAll();
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("dashboard.jsp").forward(req, res);
        }else{
            this.accountRepository.deleteAccount(accountId);
            List<Account> accounts = this.accountRepository.getAll();
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("dashboard.jsp").forward(req, res);
        }
    }

    public void showLoginForm(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, res);
    }

    public void showUpdateAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String accountId = req.getParameter("accountId");
        Optional<Account> existingAccount = this.accountRepository.findById(accountId);
        if(!existingAccount.isPresent()){
            System.out.println("RUNNING");
            List<Account> accounts = this.accountRepository.getAll();
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("dashboard.jsp").forward(req, res);
        }
        else{
            req.setAttribute("account", existingAccount.get());
            req.getRequestDispatcher("update_form.jsp").forward(req, res);
        }
    }

    public void updateAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Account updatingAccount = new Account();
        updatingAccount.setId(req.getParameter("accountId"));
        updatingAccount.setPassword(req.getParameter("password"));
        updatingAccount.setPhone(req.getParameter("phone"));
        updatingAccount.setEmail(req.getParameter("email"));
        updatingAccount.setFullName(req.getParameter("fullName"));

        this.accountRepository.updateAccount(updatingAccount.getId());

        List<Account> accounts = this.accountRepository.getAll();
        req.setAttribute("accounts", accounts);
        req.getRequestDispatcher("dashboard.jsp").forward(req, res);
    }

    public void showAddRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("role_form.jsp").forward(req, resp);
    }

    public void addRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Role role = new Role();
        role.setStatus();
        role.setDescription(req.getParameter("description"));
        role.set(req.getParameter("roleName"));
        role.setRoleId(req.getParameter("roleId"));

        this.roleRepository.addRole(role);

        List<Account> accounts = this.accountRepository.getAll();
        Account currAccount = (Account) req.getSession().getAttribute("account");
        req.setAttribute("account", currAccount);
        req.setAttribute("accounts", accounts);
        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }

}
