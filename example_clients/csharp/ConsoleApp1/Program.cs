using Newtonsoft.Json.Linq;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using System.Text;
using System.Text.Json;
using System.Text.Json.Nodes;
using ConsoleApp1;
using System.Text.RegularExpressions;
using Newtonsoft.Json;

internal class Program
{
    //-- guide: How to generate code from OpenAPI definition with Visual Studio
    //-- https://devblogs.microsoft.com/dotnet/generating-http-api-clients-using-visual-studio-connected-services/
    //-- https://www.code4it.dev/blog/openapi-code-generation-vs2019
    static async Task Main(string[] args)
    {
        Console.WriteLine("Hello, World!");

        var httpclient = new HttpClient();
        var client = new swaggerClient(httpclient);
        httpclient.BaseAddress = new Uri("http://localhost:8080");

        //-- call Hello Service - no auth is needed
        //await SimpleHello(client);

        //-- set auth headers for the user John
        var playerId = "636438380ef778617e0e5be5";
        var clientSecret = "testpass";
        var authenticationString = $"{playerId}:{clientSecret}";
        var base64EncodedAuthenticationString = Convert.ToBase64String(System.Text.ASCIIEncoding.UTF8.GetBytes(authenticationString));
        httpclient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Basic", base64EncodedAuthenticationString);

        ////-- Get Players list - will return a one length array with my data
        //await GetAllPlayers(client);

        ////-- get all matches for today
        //await GetAllMatches(client, playerId);

        //-- Start a practice match
        Card[] drawpile = {
            new Card() { Suit = CardSuit.Oracle, Value = 2 },
            new Card() { Suit = CardSuit.Oracle, Value = 3 } };
        Console.WriteLine("Creating a practice match");
        MatchCreationParams mcp = new MatchCreationParams()
        {
            Players = null,
            DrawPile = drawpile
        };
        var mcr = await client.CreateMatchAsync(mcp);
        var matchId = mcr.Id;
        Console.WriteLine($"Created match {matchId}");

        //-- Draw in a match
        IUserAction drawMove = new IUserAction()
        {
            Etype = MatchActionType.Draw
        };
        var results = await ExecuteMatchAction(client, matchId, drawMove);
        if (results != null)
            foreach (var ri in results)
            {
                if (ri.EventType == MatchEventType.Draw)
                    Console.WriteLine($"Card drawn: {JsonConvert.SerializeObject(ri.DrawCard)}");

                var rix = Newtonsoft.Json.JsonConvert.SerializeObject(ri);
                Console.WriteLine($"{ri.EventType} {rix}");
                //ri.CardRemovedFromBankIndex
                //int32, de akkor mindig latszik, double, akkor meg nem
            }
    }

    private static async Task SimpleHello(swaggerClient client)
    {
        var hello = await client.GetMessageAsync();
        Console.WriteLine(hello);
    }

    private static async Task GetAllPlayers(swaggerClient client)
    {
        var players = await client.GetPlayersAsync();
        Console.WriteLine(string.Join(", ", players.Select(p => p.Name).ToArray()));
    }

    private static async Task GetAllMatches(swaggerClient client, string playerId)
    {
        var matches = await client.GetMatchesAsync("today", null, "1");
        foreach (var match in matches)
        {
            Console.WriteLine($"{match._id} {match.TurnCount}.{match.MoveCountInTurn}/{match.MoveCount} " +
                $"{Math.Round((DateTime.Now - match.LastMoveAt).TotalHours, 1)} hours ago" + " " +
            $"{(match.CurrentPlayerId == playerId ? "active" : "not_active")}");

        }
    }

    private static async Task<ICollection<ActionResponseReturnedProps__>?> ExecuteMatchAction(swaggerClient client, string matchId, IUserAction drawMove)
    {
        try
        {
            Console.WriteLine($"Executing a '{drawMove.Etype}' Action {matchId}");
            var response = await client.ExecuteActionForMatchAsync(matchId, null, drawMove);
            return response;
        }
        catch (ApiException ex)
        {
            JObject x = JObject.Parse(ex.Response);
            Console.WriteLine(x["Error"]);
        }
        return null;
    }
}
