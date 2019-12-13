import dto.ResponseBody;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import utils.JsonUtil;


import static org.junit.Assert.*;

public class TestApi {

    private static final HttpClient client = HttpClients.createDefault();
    private static final String URL = "https://api.hh.ru/vacancies";

    /**
     * Тест, проверяющий то, что заданная апишка откликается со статусом 200 на пустой запрос
     */
    @Test
    public void smokeTest() throws Exception {
        HttpGet request = new HttpGet(URL);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();

        Assert.assertEquals(200, statusCode);
    }

    /**
     * Тест проверяющий то, что результатом поиска по невалидному тексту будет пустая выдача
     */
    @Test
    public void nonValidParameter() throws Exception{
        String notValidText = "sa23fwergj5riwsd1fgagwekrt54grtkfqw4fedvsergjsdfgserf";

        URIBuilder builder = new URIBuilder(URL);
        builder.setParameter("text", notValidText);
        HttpGet request = new HttpGet(builder.build());
        HttpResponse response = client.execute(request);
        ResponseBody body = JsonUtil.fromJson(EntityUtils.toString(response.getEntity()), ResponseBody.class);

        assertEquals(0, body.getFound());
    }

    /**
     * Тест, проверяющий корректность работы языка поисковых запросов на примере поиска вакансии по точному соответствию
     * Тест пройден в том случае, если в каждой выданной записи поле название вакансии будет точно соответствовать
     * названию в запросе
     */
    @Test
    public void exactWordMatch() throws Exception {
        String text = "NAME:!Java";
        String textInTitle = "java";

        URIBuilder builder = new URIBuilder(URL);
        builder.setParameter("text", text);
        HttpGet request = new HttpGet(builder.build());
        HttpResponse response = client.execute(request);
        ResponseBody body = JsonUtil.fromJson(EntityUtils.toString(response.getEntity()), ResponseBody.class);

        boolean allMatch = body.getItems().stream()
                .allMatch(
                        x -> x.getName().toLowerCase().contains(textInTitle)
                );
        assertTrue(allMatch);
    }

    /**
     * Тест, проверяющий корректность работы языка поисковых запросов на примере поиска вакансии в заданной компании.
     * Тест пройден в том случае, если в каждой выданной записи поле работодатель и поле вакансия будут содержать
     * переданные параметры (или же будет пустая выдача)
     */
    @Test
    public void onlyJavaDevelopersInHeadHunter() throws Exception{
        String companyName = "Headhunter";
        String name = "java";

        URIBuilder builder = new URIBuilder(URL);
        builder.setParameter("text", String.format("NAME:%s and COMPANY_NAME:%s", name, companyName));
        HttpGet request = new HttpGet(builder.build());
        HttpResponse response = client.execute(request);
        ResponseBody body = JsonUtil.fromJson(EntityUtils.toString(response.getEntity()), ResponseBody.class);

        boolean allMatch = body.getItems().stream()
                .allMatch(
                        x -> ((x.getEmployer().getName().toLowerCase().contains(companyName.toLowerCase())
                                && x.getName().toLowerCase().contains(name.toLowerCase())))
                );
        assertTrue(allMatch);
    }

    /**
     * Тест, проверяющий, что сайт не выдает ошибку сервера на некорректно большой запрос
     */
    @Test
    public void bigQuery() throws Exception {
        StringBuilder bigText = new StringBuilder();
        for (int i = 0; i < 2_000; i++) {
            bigText.append("I'm very BIG!! ");
        }
        URIBuilder builder = new URIBuilder(URL);
        builder.setParameter("text", bigText.toString());
        HttpGet request = new HttpGet(builder.build());
        HttpResponse response = client.execute(request);
        assertTrue(response.getStatusLine().getStatusCode() < 500);
    }

}
