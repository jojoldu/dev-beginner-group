/**
 * Created by jojoldu@gmail.com on 2018. 5. 14.
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

var services = process.env.SERVICES;  // Update this with your Slack service...
var channel = process.env.CHANNEL;  // And this with the Slack channel

var https = require('https');
var util = require('util');

function toYyyymmddhhmmss(date) {

    if(!date){
        return '';
    }

    function pad2(n) { return n < 10 ? '0' + n : n }
    return date.getFullYear().toString()
        + '-'+ pad2(date.getMonth() + 1)
        + '-'+ pad2( date.getDate())
        + ' '+ pad2( date.getHours())
        + ':'+ pad2( date.getMinutes())
        + ':'+ pad2( date.getSeconds());
}

var formatFields = function(string) {
    var message = JSON.parse(string),
        fields  = [],
        deploymentOverview;

    // Make sure we have a valid response
    if (message) {
        fields = [
            {
                "title" : "Task",
                "value" : message.eventTriggerName,
                "short" : true
            },
            {
                "title" : "Status",
                "value" : message.status,
                "short" : true
            },
            {
                "title" : "Application",
                "value" : message.applicationName,
                "short" : true
            },
            {
                "title" : "Deployment Group",
                "value" : message.deploymentGroupName,
                "short" : true
            },
            {
                "title" : "Region",
                "value" : message.region,
                "short" : true
            },
            {
                "title" : "Deployment Id",
                "value" : message.deploymentId,
                "short" : true
            },
            {
                "title" : "Create Time",
                "value" : toYyyymmddhhmmss(new Date(message.createTime)),
                "short" : true
            },
            {
                "title" : "Complete Time",
                "value" : toYyyymmddhhmmss(new Date((message.completeTime) ? message.completeTime : '')),
                "short" : true
            },
            {
                "title" : "Error Code",
                "value" : ((message.completeTime) ? message.errorInformation.ErrorCode : ''),
                "short" : true
            },
            {
                "title" : "Error Message",
                "value" : ((message.completeTime) ? message.errorInformation.ErrorMessage : ''),
                "short" : true
            }
        ];

        if (message.deploymentOverview) {
            deploymentOverview = JSON.parse(message.deploymentOverview);

            fields.push(
                {
                    "title" : "Succeeded",
                    "value" : deploymentOverview.Succeeded,
                    "short" : true
                },
                {
                    "title" : "Failed",
                    "value" : deploymentOverview.Failed,
                    "short" : true
                },
                {
                    "title" : "Skipped",
                    "value" : deploymentOverview.Skipped,
                    "short" : true
                },
                {
                    "title" : "In Progress",
                    "value" : deploymentOverview.InProgress,
                    "short" : true
                },
                {
                    "title" : "Pending",
                    "value" : deploymentOverview.Pending,
                    "short" : true
                }
            );
        }
    }

    return fields;
};

exports.handler = function(event, context) {

    var postData = {
        "channel": channel,
        "username": "AWS SNS via Lamda :: CodeDeploy Status",
        "text": "*" + event.Records[0].Sns.Subject + "*",
        "icon_emoji": ":aws:"
    };

    var fields = formatFields(event.Records[0].Sns.Message);
    var message = event.Records[0].Sns.Message;
    var severity = "good";

    var dangerMessages = [
        " but with errors",
        " to RED",
        "During an aborted deployment",
        "FAILED",
        "Failed to deploy application",
        "Failed to deploy configuration",
        "has a dependent object",
        "is not authorized to perform",
        "Pending to Degraded",
        "Stack deletion failed",
        "Unsuccessful command execution",
        "You do not have permission",
        "Your quota allows for 0 more running instance"];

    var warningMessages = [
        " aborted operation.",
        " to YELLOW",
        "Adding instance ",
        "Degraded to Info",
        "Deleting SNS topic",
        "is currently running under desired capacity",
        "Ok to Info",
        "Ok to Warning",
        "Pending Initialization",
        "Removed instance ",
        "Rollback of environment"
    ];

    for(var dangerMessagesItem in dangerMessages) {
        if (message.indexOf(dangerMessages[dangerMessagesItem]) != -1) {
            severity = "danger";
            break;
        }
    }

    // Only check for warning messages if necessary
    if (severity === "good") {
        for(var warningMessagesItem in warningMessages) {
            if (message.indexOf(warningMessages[warningMessagesItem]) != -1) {
                severity = "warning";
                break;
            }
        }
    }

    postData.attachments = [
        {
            "color": severity,
            "fields": fields
        }
    ];

    var options = {
        method: 'POST',
        hostname: 'hooks.slack.com',
        port: 443,
        path: services  // Defined above
    };

    var req = https.request(options, function(res) {
        res.setEncoding('utf8');
        res.on('data', function (chunk) {
            context.done(null);
        });
    });

    req.on('error', function(e) {
        console.log('problem with request: ' + e.message);
    });

    req.write(util.format("%j", postData));
    req.end();
};