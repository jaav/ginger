/*
 * MS-Soft Custom plugin for tooltips
 * Guro Khundadze 
 */

(function($) {

  $.fn.msInfoMessage = function(options) {
  	
   	// Merge default options with new ones  
    var opts = $.extend({}, $.fn.msInfoMessage.defaults, options);

	// Traverse trough all elements
    return this.each(function() {
    	// Add Info Button
    	var btn = $('<span class="'+opts.buttonClass+'">&nbsp<span>');
      	$(this).after(btn);
      	
      	// Add Hidden tooltip Div
        var message = $(this).attr('alt');
        if(message == undefined && $(this).hasClass('deleter')) message = "Verwijderen";
        if(message == undefined && $(this).hasClass('editor')) message = "Bewerken";
        if(message == undefined && $(this).hasClass('viewer')) message = "Verwijderen";
      	var tip = $('<p class="'+opts.containerClass+' triangle-border top">'+message+'</span>');
      	btn.append(tip);


      	
      	
      	var timer;
      	btn.hover(function(e){
      		if(e.type == 'mouseenter')
      		{
      			timer = setTimeout(function() {
                        tip.fadeIn('fast');
                }, opts.delay)
      		}else
      		{
      			if(timer) {
                    clearTimeout(timer);
                    timer = null
                    tip.fadeOut('fast');
                }

      		}
      	})
      	
    });
  }
  
  $.fn.msInfoMessage.defaults = {
  							delay:400,
  							buttonClass: 'infoMessageButton',
  							containerClass: 'infoMessageCont'
  						  }
   
})(jQuery);