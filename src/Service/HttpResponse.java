package Service;

public class HttpResponse
{
    public int Code;
    public String Body;

    public HttpResponse(int code)
    {
        Code = code;
    }

    public HttpResponse(int code, String body)
    {
        Code = code;
        Body = body;
    }
}
