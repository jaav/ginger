jQuery(document).ready(function(){
  var props = { autoHeight: false, collapsible:true};
  if(ginger && ginger.accordionTab){
    props.active = ginger.accordionTab -1;
  }
  else props.active = false;
	$("#accordion").accordion(props);
  $(".submitbutton").click(function(ev){
    ev.preventDefault();
    g.doSubmit(this);
  });
  /*$('.sorter').click(function(){
    var url = $(this).attr('href');
    if(url.indexOf('asc')>0) $(this).attr('href', url.substr(0, url.length-3)+'desc');
    else if(url.indexOf('desc')>0) $(this).attr('href', url.substr(0, url.length-4)+'asc');
  })*/
});

var g = {};
g.doSubmit = function(button){
  var form = button.form;
  form.submit();

}