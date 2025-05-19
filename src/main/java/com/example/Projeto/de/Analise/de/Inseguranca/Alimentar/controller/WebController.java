package com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.controller;

import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.common.dto.AvaliacaoDTO;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.model.User;
import com.example.Projeto.de.Analise.de.Inseguranca.Alimentar.service.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebController {

    private final AvaliacaoService avaliacaoService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final RelatorioEstatisticoService relatorioEstatisticoService;
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private PasswordResetService passwordResetService;

    // Construtor com todas as dependências
    @Autowired
    public WebController(
            AuthService authService,
            AvaliacaoService avaliacaoService,
            PasswordEncoder passwordEncoder,
            RelatorioEstatisticoService relatorioEstatisticoService
    ) {
        this.authService = authService;
        this.avaliacaoService = avaliacaoService;
        this.passwordEncoder = passwordEncoder;
        this.relatorioEstatisticoService = relatorioEstatisticoService;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("isAuthPage", true);
        return "login";
    }

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/questionario")
    public String showQuestionarioPage(Model model) {
        model.addAttribute("isAuthPage", true);
        model.addAttribute("avaliacaoDTO", new AvaliacaoDTO());
        return "questionario";
    }

    @GetMapping("/cadastro")
    public String showCadastroPage(Model model) {
        model.addAttribute("isAuthPage", true);
        return "cadastro";
    }

    // Rota para processar o registro do usuário
    @PostMapping("/register")
    public String registerUser(@RequestParam String nome,
                               @RequestParam String email,
                               @RequestParam String senha,
                               @RequestParam String confirmarSenha,
                               Model model) {

        email = email.trim().toLowerCase();

        // Validação: senhas diferentes
        if (!senha.equals(confirmarSenha)) {
            model.addAttribute("error", "As senhas não coincidem.");
            model.addAttribute("nome", nome);
            model.addAttribute("email", email);
            return "cadastro"; // nome do template cadastro.html
        }

        // Validação: email já cadastrado
        User existingUser = authService.findByEmail(email);
        if (existingUser != null) {
            model.addAttribute("error", "Este e-mail já está registrado.");
            model.addAttribute("nome", nome);
            model.addAttribute("email", email);
            return "cadastro";
        }

        // Cadastro do novo usuário
        User newUser = new User();
        newUser.setNome(nome);
        newUser.setEmail(email);
        newUser.setPassword(senha); // você pode adicionar hash aqui, dependendo da lógica

        authService.register(newUser);
        model.addAttribute("success", true); // opcional, se quiser usar th:if no cadastro também

        return "redirect:/login"; // redireciona com param para mostrar notificação
    }

    // Rota para processar o login do usuário
    @PostMapping("/login")
    public String login(String email, String senha, Model model) {
        // Busca o usuário pelo e-mail
        User user = authService.findByEmail(email);

        // Verifica se o usuário existe e se a senha é válida
        if (user == null || !passwordEncoder.matches(senha, user.getPassword())) {
            model.addAttribute("error", "E-mail ou senha inválidos.");
            return "login";  // Retorna para a página de login em caso de erro
        }

        // Autentica o usuário manualmente
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), senha);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Redireciona para a página do questionário
        return "redirect:/questionario";  // Redireciona para a página do questionário após login bem-sucedido
    }

    @PostMapping("/questionario")
    public String handleQuestionarioSubmit(
            @ModelAttribute AvaliacaoDTO avaliacaoDTO,
            @AuthenticationPrincipal User usuarioLogado,
            RedirectAttributes redirectAttributes
    ) {
        System.out.println("Dados recebidos: " + avaliacaoDTO); // 👈 Log para depuração
        try {
            avaliacaoService.criarAvaliacaoComUsuario(avaliacaoDTO, usuarioLogado);
            redirectAttributes.addFlashAttribute("success", "Dados salvos com sucesso!");
            return "redirect:/sucesso";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao salvar os dados: " + e.getMessage());
            return "redirect:/questionario";
        }
    }

    @GetMapping("/relatorios")
    public String relatorios(Model model) {
        return "relatorios"; // carrega o relatorios.html do templates
    }

    @GetMapping("/sucesso")
    public String mostrarSucesso() {
        return "sucesso";
    }

    @GetMapping("/redefinir-senha")
    public String showResetForm(Model model) {
        // Garante que o CSRF token está disponível
        model.addAttribute("_csrf", new CsrfToken() {
            @Override public String getToken() { return "seu-token-csrf"; }
            @Override public String getHeaderName() { return "_csrf"; }
            @Override public String getParameterName() { return "_csrf"; }
        });
        return "redefinir-senha";
    }

    @PostMapping("/redefinir-senha")
    public String handleReset(
            @RequestParam String email,
            @RequestParam String novaSenha,
            @RequestParam String confirmarSenha,
            RedirectAttributes redirectAttributes
    ) {
        // Validações
        if (!novaSenha.equals(confirmarSenha)) {
            redirectAttributes.addAttribute("error", "As senhas não coincidem");
            return "redirect:/redefinir-senha";
        }

        try {
            passwordResetService.resetPasswordByEmail(email, novaSenha);
            redirectAttributes.addAttribute("success", true);
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "E-mail não encontrado");
            return "redirect:/redefinir-senha";
        }
    }

    @GetMapping("/sobre")
    public String showPageSobre() {
        return "sobre";
    }


    @GetMapping("/contato")
    public String exibirFormulario() {
        return "contato";
    }

    @PostMapping("/enviar")
    public String enviarMensagem(@RequestParam String nome,
                                 @RequestParam String email,
                                 @RequestParam String assunto,
                                 @RequestParam String mensagem,
                                 RedirectAttributes redirectAttributes) {
        // Simulação de envio
        System.out.println("Mensagem recebida de " + nome + ": " + mensagem);
        redirectAttributes.addFlashAttribute("mensagem", "Mensagem enviada com sucesso!");
        return "redirect:/contato";
    }


}
