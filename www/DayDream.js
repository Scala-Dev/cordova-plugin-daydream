//////////////////////////////////////////
// Cache.js
// Copyright (C) 2014 Modern Alchemits OG <office@modalog.at>
//
//////////////////////////////////////////
var exec = require('cordova/exec');

var DayDream =
{
    show : function( success, error )
    {
        exec(success, error, "DayDream", "show", [])
    },
    hide : function( success, error )
    {
        exec(success, error, "DayDream", "hide", [])
    }
}

module.exports = Cache;
