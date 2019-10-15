import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import controllers.Channel;
import controllers.ChannelsBean;

@WebServlet("/servlet")
public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Lep pozdrav iz konzole");

        //List<Channe> channels;
        //channels = commentsBean.getChannels();
        //return Response.ok(channels).build();

        resp.getWriter().println("Tukaj pi\"semo na spletno stran");
    }
}