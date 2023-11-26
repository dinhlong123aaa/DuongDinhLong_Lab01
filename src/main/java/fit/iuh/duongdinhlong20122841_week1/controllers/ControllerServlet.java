package fit.iuh.duongdinhlong20122841_week1.controllers;

import java.io.*;
import java.sql.SQLException;

import fit.iuh.duongdinhlong20122841_week1.services.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "controllerServlet", urlPatterns = {"/controllerServlet"})
public class ControllerServlet extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        try {
            this.accountService = new AccountService();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action){
            case "showAddAccount": {
                this.accountService.showAddAccount(req, resp);
                break;
            }

            case "showLoginForm": {
                this.accountService.showLoginForm(req, resp);
            }

            case "showUpdateAccount": {
                this.accountService.showUpdateAccount(req, resp);
                break;
            }

            case "showAddRole": {
                this.accountService.showAddRole(req, resp);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println(action);
        switch (action){
            case "login": {
                this.accountService.login(req, resp);
                break;
            }

            case "logout": {
                this.accountService.logout(req, resp);
                break;
            }

            case "addAccount": {
                this.accountService.addAccount(req, resp);
                break;
            }

            case "addRole": {
                this.accountService.addRole(req, resp);
                break;
            }

            case "deleteUser": {
                this.accountService.deleteUser(req, resp);
                break;
            }

            case "updateAccount": {
                this.accountService.updateAccount(req, resp);
                break;
            }
            default: {
//                throw new RuntimeException("Action not found");
                break;
            }
        }
    }


}